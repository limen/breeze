package com.limengxiang.breeze.manager;

import com.limengxiang.breeze.SpringContextUtil;
import com.limengxiang.breeze.concurrent.NamedThreadFactory;
import com.limengxiang.breeze.config.Config;
import com.limengxiang.breeze.domain.coordinator.ICoordinator;
import com.limengxiang.breeze.domain.executor.ExecResult;
import com.limengxiang.breeze.domain.job.IJobIdProvider;
import com.limengxiang.breeze.domain.job.IJobQueue;
import com.limengxiang.breeze.domain.job.JobPostExecHandler;
import com.limengxiang.breeze.model.JobService;
import com.limengxiang.breeze.model.entity.db.JobEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Slf4j
public class JobManager {

    private static Config config;

    private static ICoordinator coordinator;
    private static JobService jobService;
    private static IJobIdProvider jobIdManager;
    private static IJobQueue jobQueue;
    private static JobPostExecHandler jobPostExecHandler;
    private static ExecutorManager executorManager;

    private static final int scannerWaitSeconds = 5;
    private static final long scannerWaitMillis = scannerWaitSeconds * 1000;

    private static final int availableProcessors = Runtime.getRuntime().availableProcessors();

    private static volatile boolean shutdown = false;

    private static final ThreadPoolExecutor jobConsumerThreadPool = new ThreadPoolExecutor(
            2 * availableProcessors,
            2 * availableProcessors,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("job-consumer")
    );

    // scanner wait for N seconds, so N+1 workers are enough
    private static final ThreadPoolExecutor jobScannerThreadPool = new ThreadPoolExecutor(
            scannerWaitSeconds + 1,
            scannerWaitSeconds + 1,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("job-scanner")
    );

    public static void shutdown() {
        shutdown = true;
        log.info("Shutdown job consumer thread pool");
        jobConsumerThreadPool.shutdown();
        try {
            log.info("Await shutdown for 30 secs");
            jobConsumerThreadPool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Shutdown job scanner thread pool");
        jobScannerThreadPool.shutdown();
        try {
            log.info("Await shutdown for 30 secs");
            jobScannerThreadPool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void start(Config conf) {

        config = conf;
        coordinator = SpringContextUtil.getBean(ICoordinator.class);
        jobService = SpringContextUtil.getBean(JobService.class);
        jobIdManager = SpringContextUtil.getBean(IJobIdProvider.class);
        jobQueue = SpringContextUtil.getBean(IJobQueue.class);
        jobPostExecHandler = SpringContextUtil.getBean(JobPostExecHandler.class);
        executorManager = SpringContextUtil.getBean(ExecutorManager.class);

        NamedThreadFactory.newThread("job-scanner-dispatcher", new JobScannerDispatcher()).start();
        NamedThreadFactory.newThread("job-queue-dispatcher", new JobQueueDispatcher()).start();
    }

    static class JobScannerDispatcher implements Runnable {

        @Override
        public void run() {
            log.info("Job scanner dispatcher is running");
            while (!shutdown) {
                try {
                    if (jobScannerThreadPool.getQueue().size() > 0) {
                        Thread.sleep(10);
                        continue;
                    }
                    ICoordinator.DutyInfo dutyInfo = coordinator.acquireDuty();
                    if (dutyInfo == null || !dutyInfo.on() || dutyInfo.negative()) {
                        Thread.sleep(10);
                        continue;
                    }
                    jobScannerThreadPool.execute(new JobScanner(dutyInfo));
                } catch (Exception ex) {
                    log.warn("Job scanner dispatcher accounts error:" + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    static class JobScanner implements Runnable {

        private ICoordinator.DutyInfo dutyInfo;

        JobScanner(ICoordinator.DutyInfo dutyInfo) {
            this.dutyInfo = dutyInfo;
        }

        @Override
        public void run() {
            try {
                int jobNum = 0;
                Long jobIdLow = null;
                long jobIdUp = jobIdManager.lastOnTime(dutyInfo.tickTo());
                long tickToMillis = dutyInfo.getTickTo() * 1000;
                long firstJobIdForDuty = jobIdManager.firstOnTime(dutyInfo.tickFrom());
                Date startAt = new Date();
                while (!shutdown) {
                    long queueSize = jobQueue.size();
                    if (queueSize > config.getJobQueueBusySize()) {
                        Thread.sleep(10);
                        continue;
                    }
                    List<Long> jobIds = jobService.findJobIdsInRange(
                            jobIdLow == null ? firstJobIdForDuty : jobIdLow,
                            jobIdUp,
                            config.getJobScanBatchSize());
                    if (jobIds != null && !jobIds.isEmpty()) {
                        jobQueue.push(jobIds);
                        // add 1 to step over the last one
                        jobIdLow = (Long) jobIds.get(jobIds.size() - 1) + 1;
                        jobNum += jobIds.size();
                    }
                    if (jobIds == null || jobIds.size() < config.getJobScanBatchSize()) {
                        // wait for a while
                        if (System.currentTimeMillis() - tickToMillis > scannerWaitMillis) {
                            break;
                        }
                        Thread.sleep(10);
                    }
                }
                log.info("Coordinator finishes duty {}:{}, started at:{}, publishes jobs {}", dutyInfo.tickFrom(), dutyInfo.tickTo(), startAt, jobNum);
            } catch (Throwable ex) {
                log.warn("Job scanner accounts error:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    static class JobQueueDispatcher implements Runnable {

        @Override
        public void run() {
            log.info("Job queue dispatcher is running");
            while (!shutdown) {
                try {
                    // If the pool is busy, wait for a while
                    if (jobConsumerThreadPool.getQueue().size() > config.getJobThreadPoolBusySize()) {
                        Thread.sleep(10);
                        continue;
                    }
                    Long jobId = jobQueue.pop();
                    if (jobId == null) {
                        Thread.sleep(1);
                        continue;
                    }
                    JobConsumer jobConsumer = new JobConsumer();
                    jobConsumer.setJobId(jobId);
                    jobConsumer.setJobPostExecHandler(jobPostExecHandler);
                    jobConsumerThreadPool.execute(jobConsumer);
                } catch (Throwable ex) {
                    log.warn("Job dispatcher accounts error:" + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    @Data
    static class JobConsumer implements Runnable {

        private Long jobId;
        private JobPostExecHandler jobPostExecHandler;

        @Override
        public void run() {
            JobEntity jobEntity = jobService.find(jobId);
            if (jobEntity == null) {
                return;
            }
            ExecResult result = executorManager.getExecutor(jobEntity.getExecutorId()).exec(jobEntity);
            jobPostExecHandler.handle(jobId, result);
        }
    }

}

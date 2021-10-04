package com.limengxiang.breeze.domain.job.repository;

import com.limengxiang.breeze.domain.job.model.JobEntity;
import com.limengxiang.breeze.domain.job.model.JobExecLogEntity;
import com.limengxiang.breeze.domain.job.model.JobStatValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobRepository {

    void insert(@Param("jobId") Object jobId,
                @Param("jobName") String jobName,
                @Param("scheduleAt") Object schedule,
                @Param("executorId") Long executorId,
                @Param("params") String params);

    List<Long> queryJobIdsInRange(@Param("jobIdLow") Long jobIdLow,
                                  @Param("jobIdUp") Long jobIdUp,
                                  @Param("limit") int limit);

    Long lastJobIdInRange(@Param("jobIdLow") Long jobIdLow,
                          @Param("jobIdUp") Long jobIdUp);

    JobEntity queryByJobId(@Param("jobId") Long jobId);

    List<JobExecLogEntity> queryJobLogs(@Param("jobId") Long jobId);

    List<JobStatValue> queryJobStat(@Param("jobIdLow") Long jobIdLow,
                                    @Param("jobIdUp") Long jobIdUp,
                                    @Param(("execStatus")) int execStatus);
}

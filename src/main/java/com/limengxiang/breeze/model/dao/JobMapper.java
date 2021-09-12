package com.limengxiang.breeze.model.dao;

import com.limengxiang.breeze.model.entity.JobEntity;
import com.limengxiang.breeze.model.entity.JobExecLogEntity;
import com.limengxiang.breeze.model.entity.JobStatEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper {

    void insert(@Param("jobId") Object jobId,
                @Param("jobName") String jobName,
                @Param("scheduleAt") Object schedule,
                @Param("executorId") Long executorId,
                @Param("params") String params);

    List<String> queryRange(@Param("jobIdLow") Object jobIdLow,
                            @Param("jobIdUp") Object jobIdUp,
                            @Param("limit") int limit);

    Long lastJobIdInRange(@Param("jobIdLow") Object jobIdLow,
                          @Param("jobIdUp") Object jobIdUp);

    JobEntity jobDetail(@Param("jobId") Object jobId);

    List<JobExecLogEntity> jobLogs(@Param("jobId") Object jobId);

    List<JobStatEntity> jobStat(@Param("jobIdLow") Object jobIdLow,
                                @Param("jobIdUp") Object jobIdUp,
                                @Param(("execStatus")) int execStatus);
}

package com.momentum.batch.database.repository;

import com.hlag.fis.batch.domain.StepExecutionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StepExecutionInfoRepository extends PagingAndSortingRepository<StepExecutionInfo, String> {

    @Query("select s from StepExecutionInfo s where s.jobExecutionInfo.id = :jobExecutionId")
    Page<StepExecutionInfo> findByJobId(@Param("jobExecutionId") String jobExecutionId, Pageable pageable);

    @Query("select count(s) from StepExecutionInfo s where s.jobExecutionInfo.id = :jobExecutionId")
    Long countByJobId(@Param("jobExecutionId") String jobExecutionId);

    @Query("select s from StepExecutionInfo s where" +
            " s.jobExecutionInfo.jobExecutionInstance.jobName = :jobName and s.jobExecutionInfo.jobExecutionId = :jobExecutionId and" +
            " s.stepName = :stepName and s.stepExecutionId = :stepExecutionId and" +
            " s.startTime = :startTime")
    Optional<StepExecutionInfo> findByStepInfo(@Param("jobName") String jobName,
                                               @Param("jobExecutionId") Long jobExecutionId,
                                               @Param("stepName") String stepName,
                                               @Param("stepExecutionId") Long stepExecutionId,
                                               @Param("startTime") LocalDateTime startTime);
}

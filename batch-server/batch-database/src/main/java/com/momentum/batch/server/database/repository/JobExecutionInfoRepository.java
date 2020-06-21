package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobExecutionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * Job execution repository.
 *
 * @author Jens Vogt (jensvogt47@gmail.ccom)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
@Repository
public interface JobExecutionInfoRepository extends PagingAndSortingRepository<JobExecutionInfo, String> {

    Page<JobExecutionInfo> findAll(@NonNull Pageable pageable);

    Optional<JobExecutionInfo> findById(@NonNull String id);

    @Query("select j from JobExecutionInfo j where j.jobDefinition.id = :jobDefinitionId")
    Page<JobExecutionInfo> findByJobDefinition(@Param("jobDefinitionId") String jobDefinitionId, Pageable pageable);

    @Query("select count(j) from JobExecutionInfo j where j.lastUpdated < :cutOff")
    long countByLastUpdated(@Param("cutOff") Date cutOff);

    @Query("select max(j.jobExecutionId) from JobExecutionInfo j where j.jobDefinition.name = :jobName")
    Optional<Long> getLastExecutionId(@Param("jobName") String jobName);

    @Query("select j.jobExecutionId from JobExecutionInfo j where j.jobName = :jobName order by j.startTime desc")
    Page<JobExecutionInfo> findLastExecutionInfo(@Param("jobName") String jobName, Pageable pageable);
}


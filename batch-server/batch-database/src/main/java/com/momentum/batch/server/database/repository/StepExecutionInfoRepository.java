package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.StepExecutionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StepExecutionInfoRepository extends PagingAndSortingRepository<StepExecutionInfo, String>, QueryByExampleExecutor<StepExecutionInfo> {

    @Query("select s from StepExecutionInfo s where s.jobExecutionInfo.id = :jobExecutionId")
    Page<StepExecutionInfo> findByJobId(@Param("jobExecutionId") String jobExecutionId, Pageable pageable);
}

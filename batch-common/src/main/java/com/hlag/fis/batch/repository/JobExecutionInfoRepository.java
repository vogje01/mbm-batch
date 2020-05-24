package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.JobExecutionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface JobExecutionInfoRepository extends PagingAndSortingRepository<JobExecutionInfo, String> {

    @Query("select j from JobExecutionInfo j where j.deleted = false")
    Page<JobExecutionInfo> findAllNotDeleted(Pageable pageable);

    Page<JobExecutionInfo> findAll(Pageable pageable);

    Optional<JobExecutionInfo> findById(String id);

    @Query("select count(j) from JobExecutionInfo j where j.deleted = true or j.lastUpdated < :cutOff")
    long countByLastUpdated(@Param("cutOff") Date cutOff);
}

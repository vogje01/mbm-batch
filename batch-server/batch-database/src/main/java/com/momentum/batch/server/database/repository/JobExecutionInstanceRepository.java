package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobExecutionInstance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionInstanceRepository extends PagingAndSortingRepository<JobExecutionInstance, String> {

}

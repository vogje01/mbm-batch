package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobInstanceInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionInstanceRepository extends PagingAndSortingRepository<JobInstanceInfo, String> {

}

package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobExecutionContext;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionContextRepository extends PagingAndSortingRepository<JobExecutionContext, String> {

}

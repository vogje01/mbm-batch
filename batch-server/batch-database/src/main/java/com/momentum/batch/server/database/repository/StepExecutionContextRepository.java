package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.StepExecutionContext;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepExecutionContextRepository extends PagingAndSortingRepository<StepExecutionContext, String> {

}

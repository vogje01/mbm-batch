package com.momentum.batch.database.repository;

import com.momentum.batch.database.domain.JobInstanceInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobInstanceInfoRepository extends PagingAndSortingRepository<JobInstanceInfo, String> {

}

package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.JobInstanceInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobInstanceInfoRepository extends PagingAndSortingRepository<JobInstanceInfo, String> {

}

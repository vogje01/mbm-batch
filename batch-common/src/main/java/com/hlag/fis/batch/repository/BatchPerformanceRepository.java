package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.BatchPerformance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchPerformanceRepository extends PagingAndSortingRepository<BatchPerformance, String> {

}

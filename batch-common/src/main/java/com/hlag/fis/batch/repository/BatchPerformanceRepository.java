package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.BatchPerformance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface BatchPerformanceRepository extends PagingAndSortingRepository<BatchPerformance, String> {

    @Query("select b from BatchPerformance b where b.qualifier = :qualifier and b.metric = :metric")
    Optional<BatchPerformance> findByQualifierAndMetric(@Param("qualifier") String qualifier, @Param("metric") String metric);

    @Query("select b from BatchPerformance b where b.qualifier = :qualifier and b.metric = :metric and b.timestamp = :timestamp")
    Optional<BatchPerformance> findByQualifierAndMetricAndTimestamp(@Param("qualifier") String qualifier, @Param("metric") String metric, @Param("timestamp") Timestamp timestamp);
}

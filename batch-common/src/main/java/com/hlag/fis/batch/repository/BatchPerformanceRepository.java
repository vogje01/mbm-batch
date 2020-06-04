package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.domain.BatchPerformanceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface BatchPerformanceRepository extends PagingAndSortingRepository<BatchPerformance, String> {

    @Query("select count(b) from BatchPerformance b where b.type = :type and b.timestamp < :cutOff")
    long countByTimestamp(@Param("type") BatchPerformanceType type, @Param("cutOff") Timestamp cutOff);

    @Query("select count(b) from BatchPerformance b where b.type = :type")
    long countByType(@Param("type") BatchPerformanceType type);

    @Query("select b from BatchPerformance b where b.qualifier = :qualifier and b.metric = :metric")
    Optional<BatchPerformance> findByQualifierAndMetric(@Param("qualifier") String qualifier, @Param("metric") String metric);

    @Query("select b from BatchPerformance b where b.qualifier = :qualifier and b.metric = :metric and b.timestamp = :timestamp")
    Optional<BatchPerformance> findByQualifierAndMetricAndTimestamp(@Param("qualifier") String qualifier, @Param("metric") String metric, @Param("timestamp") Timestamp timestamp);
}

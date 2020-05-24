package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface AgentPerformanceRepository extends PagingAndSortingRepository<AgentPerformance, String> {

    @Query("select count(a) from AgentPerformance a where a.type = :type order by a.lastUpdate")
    long countByType(@Param("type") AgentPerformanceType type);

    @Query("select count(a) from AgentPerformance a where a.type = :type and a.lastUpdate < :cutOff")
    long countByLastUpdated(@Param("type") AgentPerformanceType type, @Param("cutOff") Timestamp cutOff);

    @Query("select a from AgentPerformance a where a.nodeName = :nodeName and a.type = :type and a.lastUpdate = :lastUpdate")
    Optional<AgentPerformance> findByTimestamp(@Param("nodeName") String nodeName,
                                               @Param("type") AgentPerformanceType type,
                                               @Param("lastUpdate") Timestamp lastUpdate);

    @Query("select a from AgentPerformance a where a.nodeName = :nodeName")
    Page<AgentPerformance> findByNodeName(@Param("nodeName") String nodeName, Pageable pageable);

    @Query("select a from AgentPerformance a where a.nodeName = :nodeName and a.type = :type")
    Page<AgentPerformance> findByNodeNameAndType(@Param("nodeName") String nodeName,
                                                 @Param("type") AgentPerformanceType type,
                                                 Pageable pageable);

    @Query("select a from AgentPerformance a where a.nodeName = :nodeName and a.type = :type and " +
            "a.lastUpdate >= :startTime and a.lastUpdate <= :endTime order by a.lastUpdate")
    Page<AgentPerformance> findByNodeNameAndTypeAndTimeRange(@Param("nodeName") String nodeName,
                                                             @Param("type") AgentPerformanceType type,
                                                             @Param("startTime") Timestamp startTime,
                                                             @Param("endTime") Timestamp endTime,
                                                             Pageable pageable);
}

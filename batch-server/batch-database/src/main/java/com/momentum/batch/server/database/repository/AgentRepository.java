package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentRepository extends PagingAndSortingRepository<Agent, String> {

    @Query("select a from Agent a left join a.schedules s where s.id = :jobScheduleId")
    Page<Agent> findByScheduleId(@Param("jobScheduleId") String jobScheduleId, Pageable pageable);

    @Query("select a from Agent a where a.nodeName = :nodeName")
    Optional<Agent> findByNodeName(@Param("nodeName") String nodeName);

    @Query("select a from Agent a left join a.agentGroups ag where ag.id = :agentGroupId")
    Page<Agent> findByAgentGroupId(@Param("agentGroupId") String agentGroupId, Pageable pageable);

    @Query("select a.nodeName from Agent a")
    List<String> findAllAgentNames();

}

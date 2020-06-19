package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.AgentGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AgentGroupRepository extends PagingAndSortingRepository<AgentGroup, String> {

    @Query("select ag from AgentGroup ag left join ag.schedules s where s.id = :jobScheduleId")
    Page<AgentGroup> findByScheduleId(@Param("jobScheduleId") String jobScheduleId, Pageable pageable);

    @Query("select ag from AgentGroup ag where ag.name = :name and ag.active = true")
    Optional<AgentGroup> findByName(@Param("name") String name);

    @Query("select ag from AgentGroup ag left join ag.agents a where a.id = :agentId")
    Page<AgentGroup> findByAgentId(@Param("agentId") String agentId, Pageable pageable);
}

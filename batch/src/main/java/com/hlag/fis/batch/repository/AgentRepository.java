package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.Agent;
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

    @Query("select a from Agent a where a.active = :active")
    Page<Agent> findAllActive(Pageable pageable, @Param("active") boolean active);

    @Query("select a from Agent a where a.nodeName = :nodeName")
    Optional<Agent> findByNodeName(@Param("nodeName") String nodeName);

    @Query("select a.nodeName from Agent a")
    List<String> findAllAgentNames();

    @Query("select count(j) from Agent a left join a.schedules j where a.id = :id")
    Long countSchedules(@Param("id") String id);
}

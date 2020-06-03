package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.AgentGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AgentGroupRepository extends PagingAndSortingRepository<AgentGroup, String> {

    @Query("select j from AgentGroup j where j.active = true")
    Page<AgentGroup> findAllActive(Pageable pageable);

    @Query("select j from AgentGroup j where j.name = :name and j.active = true")
    Optional<AgentGroup> findByName(@Param("name") String name);
}

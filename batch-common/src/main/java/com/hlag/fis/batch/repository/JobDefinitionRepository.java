package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.JobDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobDefinitionRepository extends PagingAndSortingRepository<JobDefinition, String> {

    @Query("select j from JobDefinition j where j.active = true")
    Page<JobDefinition> findAllActive(Pageable pageable);

    @Query("select j from JobDefinition j where j.name = :name and j.active = true")
    Optional<JobDefinition> findByName(@Param("name") String name);
}

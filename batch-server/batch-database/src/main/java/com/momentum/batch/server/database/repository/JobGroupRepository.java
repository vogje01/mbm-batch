package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobGroupRepository extends PagingAndSortingRepository<JobGroup, String> {

    @Query("select g from JobGroup g where g.name = :name")
    Optional<JobGroup> findByName(@Param("name") String name);

    @Query("select jg from JobGroup jg left join jg.jobDefinitions j where j.id = :jobDefinitionId")
    Page<JobGroup> findByJobDefinition(@Param("jobDefinitionId") String jobDefinitionId, Pageable pageable);

}

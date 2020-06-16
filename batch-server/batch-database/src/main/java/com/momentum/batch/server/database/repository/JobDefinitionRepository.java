package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobDefinitionRepository extends PagingAndSortingRepository<JobDefinition, String> {

    @Query("select j from JobDefinition j where j.name = :name and j.active = true")
    Optional<JobDefinition> findByName(@Param("name") String name);

    @Query("select j from JobDefinition j where j.fileName = :fileName")
    List<JobDefinition> findByFileName(@Param("fileName") String fileName);

    @Query("select j from JobDefinition j join j.jobGroups jg where jg.id = :jobGroupId")
    Page<JobDefinition> findByJobGroup(@Param("jobGroupId") String jobGroupId, Pageable pageable);

    @Query("select j from JobDefinition j join j.jobGroups jg where jg.id <> :jobGroupId")
    Page<JobDefinition> findWithoutJobGroup(@Param("jobGroupId") String jobGroupId, Pageable pageable);
}

package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.JobGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "jobgroups", path = "jobgroups")
public interface JobGroupRepository extends PagingAndSortingRepository<JobGroup, String> {

    @Query("select j from JobGroup j where j.active = true")
    Page<JobGroup> findAllActive(Pageable pageable);

    @Query("select j from JobGroup j where j.name = :name")
    Optional<JobGroup> findByName(@Param("name") String name);
}

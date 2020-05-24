package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.JobSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "jobschedules", path = "jobschedules")
public interface JobScheduleRepository extends PagingAndSortingRepository<JobSchedule, String> {

    @Query("select j from JobSchedule j left join j.jobDefinition d where j.active = :active")
    Page<JobSchedule> findAllActive(@Param("active") boolean active, Pageable pageable);

    @Query("select j from JobSchedule j left join j.jobDefinition d where d.jobGroup.name = :groupName and d.name = :jobName")
    Optional<JobSchedule> findByGroupAndName(@Param("groupName") String groupName, @Param("jobName") String jobName);

    @Query("select j from JobSchedule j left join j.jobDefinition d left join j.agents a where a.id = :agentId")
    List<JobSchedule> findByAgent(@Param("agentId") String agentId);

    @Query("select j from JobSchedule j left join j.jobDefinition d left join j.agents a where a.id = :agentId and a.active = true and j.active = true")
    List<JobSchedule> findActiveByAgent(@Param("agentId") String agentId);

    @Query("select j from JobSchedule j left join j.jobDefinition d where d.id = :jobDefinitionId")
    List<JobSchedule> findByJobDefinitionId(@Param("jobDefinitionId") String jobDefinitionId);

    @Query("select count(a) from JobSchedule j left join j.agents a where j.id = :scheduleId")
    long countAgents(@Param("scheduleId") String scheduleId);

    @Query("select j from JobSchedule j left join j.agents a where a.id = :agentId")
    Page<JobSchedule> findByAgentId(@Param("agentId") String agentId, Pageable pageable);

    @Query("select j from JobSchedule j where j.name = :name")
    Optional<JobSchedule> findByName(@Param("name") String name);
}

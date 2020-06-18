package com.momentum.batch.server.database.repository;

import com.momentum.batch.common.domain.JobScheduleType;
import com.momentum.batch.server.database.domain.JobSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobScheduleRepository extends PagingAndSortingRepository<JobSchedule, String> {

    @Query("select j from JobSchedule j join fetch j.jobDefinition d join fetch d.jobGroups g where g.name = :groupName and d.name = :jobName")
    Optional<JobSchedule> findByGroupAndName(@Param("groupName") String groupName, @Param("jobName") String jobName);

    @Query("select j from JobSchedule j where j.type = :type")
    List<JobSchedule> findByType(@Param("type") JobScheduleType type);

    @Query("select j from JobSchedule j join j.jobDefinition d left join d.jobGroups g join j.agents a where a.id = :agentId")
    List<JobSchedule> findActiveByAgent(@Param("agentId") String agentId);

    @Query("select j from JobSchedule j left join j.jobDefinition d where d.id = :jobDefinitionId")
    List<JobSchedule> findByJobDefinitionId(@Param("jobDefinitionId") String jobDefinitionId);

    @Query("select j from JobSchedule j left join j.agents a where a.id = :agentId")
    Page<JobSchedule> findByAgentId(@Param("agentId") String agentId, Pageable pageable);

    @Query("select j from JobSchedule j where j.name = :name")
    Optional<JobSchedule> findByName(@Param("name") String name);

}

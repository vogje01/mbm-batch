package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.projection.JobStatusProjection;
import com.momentum.batch.server.database.domain.projection.StepStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Repository
public interface JobStatusRepository extends JpaRepository<JobExecutionInfo, String> {

    @Query("select j.status as status, count(j) as value from JobExecutionInfo j group by j.status")
    List<JobStatusProjection> findJobStatus();

    @Query("select s.status as status, count(s) as value from StepExecutionInfo s group by s.status")
    List<StepStatusProjection> findStepStatus();
}

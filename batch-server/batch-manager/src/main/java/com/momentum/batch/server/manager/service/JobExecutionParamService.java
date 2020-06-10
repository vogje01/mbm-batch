package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobExecutionParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobExecutionParamService {

    Page<JobExecutionParam> findAll(Pageable pageable);

    Page<JobExecutionParam> findByJobId(String jobId, Pageable pageable);

    Optional<JobExecutionParam> findById(String paramId);

    void deleteById(String logId);
}

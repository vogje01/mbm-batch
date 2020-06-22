package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.JobStatisticDto;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public interface StatusService {

    JobStatisticDto getJobStatistics();
}

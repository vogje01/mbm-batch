package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

public interface AgentPerformanceService {

    Page<AgentPerformance> findByNodeName(String nodeName, Pageable pageable);

    Page<AgentPerformance> findByNodeNameAndType(String nodeName, AgentPerformanceType type, Pageable pageable);

    Page<AgentPerformance> findByNodeNameAndTypeAndTimeRange(String nodeName, AgentPerformanceType type,
                                                             Timestamp startTime, Timestamp endTime, Pageable pageable);
}

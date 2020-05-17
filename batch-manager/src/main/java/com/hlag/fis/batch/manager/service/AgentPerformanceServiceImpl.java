package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AgentPerformanceServiceImpl implements AgentPerformanceService {

    private AgentPerformanceRepository agentPerformanceRepository;

    @Autowired
    public AgentPerformanceServiceImpl(AgentPerformanceRepository agentPerformanceRepository) {
        this.agentPerformanceRepository = agentPerformanceRepository;
    }

    @Override
    @Cacheable(cacheNames = "AgentPerformance")
    public Page<AgentPerformance> findByNodeName(String nodeName, Pageable pageable) {
        return agentPerformanceRepository.findByNodeName(nodeName, pageable);
    }

    @Override
    @Cacheable(cacheNames = "AgentPerformance")
    public Page<AgentPerformance> findByNodeNameAndType(String nodeName, AgentPerformanceType type, Pageable pageable) {
        return agentPerformanceRepository.findByNodeNameAndType(nodeName, type, pageable);
    }

    @Override
    @Cacheable(cacheNames = "AgentPerformance")
    public Page<AgentPerformance> findByNodeNameAndTypeAndTimeRange(String nodeName, AgentPerformanceType type,
                                                                    Timestamp startTime, Timestamp endTime, Pageable pageable) {
        return agentPerformanceRepository.findByNodeNameAndTypeAndTimeRange(nodeName, type, startTime, endTime, pageable);
    }

}

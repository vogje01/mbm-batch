package com.momentum.batch.server.manager.service;

import com.momentum.batch.domain.BatchPerformanceType;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BatchPerformanceServiceImpl implements BatchPerformanceService {

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public BatchPerformanceServiceImpl(BatchPerformanceRepository batchPerformanceRepository) {
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    @Override
    @Cacheable(cacheNames = "BatchPerformance")
    public List<BatchPerformance> findData(String nodeName, BatchPerformanceType type, String metric, Timestamp startTime, Timestamp endTime) {
        return batchPerformanceRepository.findData(nodeName, type, metric, startTime, endTime);
    }
}

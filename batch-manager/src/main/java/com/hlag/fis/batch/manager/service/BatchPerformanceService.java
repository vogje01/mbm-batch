package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.domain.BatchPerformanceType;

import java.sql.Timestamp;
import java.util.List;

public interface BatchPerformanceService {

    List<BatchPerformance> findData(String nodeName, BatchPerformanceType type, String metric, Timestamp startTime, Timestamp endTime);
}

package com.momentum.batch.server.manager.service;


import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.domain.BatchPerformanceType;

import java.sql.Timestamp;
import java.util.List;

public interface BatchPerformanceService {

    List<BatchPerformance> findData(String nodeName, BatchPerformanceType type, String metric, Timestamp startTime, Timestamp endTime);
}

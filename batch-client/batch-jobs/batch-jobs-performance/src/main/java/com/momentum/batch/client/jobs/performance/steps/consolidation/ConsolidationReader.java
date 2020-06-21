package com.momentum.batch.client.jobs.performance.steps.consolidation;

import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import com.momentum.batch.common.domain.BatchPerformanceType;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConsolidationReader {

    @Value("${performance.batch.consolidation.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    ConsolidationReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader(BatchPerformanceType batchPerformanceType, long interval) {
        String queryString = "select " +
                "b.qualifier, b.metric, avg(b.value), from_unixtime(floor((unix_timestamp(b.timestamp) / :interval)) * :interval) as timestamp " +
                "from BatchPerformance b " +
                "where b.type = :type " +
                "group by b.metric, b.qualifier, from_unixtime(floor((unix_timestamp(b.timestamp) / :interval)) * :interval)";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", batchPerformanceType);
        parameters.put("interval", interval);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

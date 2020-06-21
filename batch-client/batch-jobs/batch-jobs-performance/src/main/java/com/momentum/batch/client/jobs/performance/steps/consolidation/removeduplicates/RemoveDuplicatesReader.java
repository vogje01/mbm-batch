package com.momentum.batch.client.jobs.performance.steps.consolidation.removeduplicates;

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
public class RemoveDuplicatesReader {

    @Value("${performance.batch.consolidation.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    RemoveDuplicatesReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader(BatchPerformanceType batchPerformanceType) {
        String queryString = "select b FROM BatchPerformance b where b.type = :type";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", batchPerformanceType);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

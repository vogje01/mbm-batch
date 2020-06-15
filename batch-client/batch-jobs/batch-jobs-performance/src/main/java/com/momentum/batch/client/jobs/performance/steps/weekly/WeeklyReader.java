package com.momentum.batch.client.jobs.performance.steps.weekly;

import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import com.momentum.batch.server.database.domain.BatchPerformanceType;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeeklyReader {

    @Value("${performance.batch.weekly.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    WeeklyReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select " +
                "b.qualifier, b.metric, avg(b.value), from_unixtime(floor((unix_timestamp(b.timestamp) / :interval)) * :interval) as timestamp " +
                "from BatchPerformance b " +
                "where b.type = :type " +
                "group by b.metric, b.qualifier, from_unixtime(floor((unix_timestamp(b.timestamp) / :interval)) * :interval)";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", BatchPerformanceType.DAILY);
        parameters.put("interval", 3600L);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

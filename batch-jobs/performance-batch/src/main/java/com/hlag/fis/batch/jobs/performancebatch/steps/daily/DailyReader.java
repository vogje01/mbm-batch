package com.hlag.fis.batch.jobs.performancebatch.steps.daily;

import com.hlag.fis.batch.domain.BatchPerformanceType;
import com.hlag.fis.batch.reader.CursorReaderBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class DailyReader {

    @Value("${consolidation.batch.daily.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    DailyReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select " +
                "b.qualifier, b.metric, avg(b.value), from_unixtime(floor((unix_timestamp(b.timestamp) / :interval)) * :interval) as timestamp " +
                "from BatchPerformance b " +
                "where b.type = :type " +
                "group by b.metric, b.qualifier, from_unixtime(floor((unix_timestamp(b.timestamp) / :interval)) * :interval)";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", BatchPerformanceType.RAW);
        parameters.put("interval", 300L);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}
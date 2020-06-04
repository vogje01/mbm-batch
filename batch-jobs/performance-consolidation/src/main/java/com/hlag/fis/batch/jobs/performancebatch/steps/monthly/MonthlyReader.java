package com.hlag.fis.batch.jobs.performancebatch.steps.monthly;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class MonthlyReader {

    @Value("${consolidation.batch.monthly.chunkSize}")
    private int chunkSize;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    MonthlyReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select " +
                "b.qualifier, b.metric, avg(b.value), from_unixtime(floor((unix_timestamp(b.timestamp) / :interval)) * :interval) as timestamp " +
                "from BatchPerformance b " +
                "where b.metric like '%.weekly' " +
                "group by b.metric, b.qualifier, from_unixtime(floor((unix_timestamp(b.timestamp) / :interval)) * :interval)";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("interval", 24 * 3600L);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

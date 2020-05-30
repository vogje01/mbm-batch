package com.hlag.fis.batch.jobs.performancedataconsolidation.steps.stepcount;

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
public class StepCountReader {

    @Value("${consolidation.batch.stepCount.chunkSize}")
    private int chunkSize;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    StepCountReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select j.hostName, count(j.hostName), from_unixtime(floor((unix_timestamp(j.startTime) / :interval)) * :interval) as lastUpdate "
                + "from JobExecutionInfo j "
                + "group by j.hostName, from_unixtime(floor((unix_timestamp(j.startTime) / :interval)) * :interval) "
                + "order by lastUpdate";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("interval", 300L);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

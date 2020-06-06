package com.hlag.fis.batch.jobs.performancebatch.steps.stepcount;

import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class StepCountReader {

    @Value("${consolidation.batch.stepCount.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    StepCountReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select s.nodeName, count(s.nodeName), from_unixtime(floor((unix_timestamp(s.startTime) / :interval)) * :interval) as timestamp "
                + "from StepExecutionInfo s "
                + "where s.nodeName is not null "
                + "group by s.nodeName, from_unixtime(floor((unix_timestamp(s.startTime) / :interval)) * :interval)";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("interval", 300L);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

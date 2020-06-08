package com.momentum.batch.client.jobs.performance.steps.jobcount;

import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCountNodeReader {

    @Value("${performance.batch.jobCount.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    JobCountNodeReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select j.nodeName, count(j.nodeName), from_unixtime(floor((unix_timestamp(j.startTime) / :interval)) * :interval) as timestamp "
                + "from JobExecutionInfo j "
                + "group by j.nodeName, from_unixtime(floor((unix_timestamp(j.startTime) / :interval)) * :interval)";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("interval", 300L);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

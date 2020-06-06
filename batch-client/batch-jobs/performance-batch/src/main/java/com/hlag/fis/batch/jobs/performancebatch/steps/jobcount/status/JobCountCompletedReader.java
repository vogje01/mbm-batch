package com.hlag.fis.batch.jobs.performancebatch.steps.jobcount.status;

import com.momentum.batch.client.common.job.reader.CursorReaderBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCountCompletedReader {

    @Value("${consolidation.batch.jobStatus.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    JobCountCompletedReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select j.nodeName, count(j.nodeName), from_unixtime(floor((unix_timestamp(j.startTime) / :interval)) * :interval) as timestamp "
                + "from JobExecutionInfo j "
                + "where j.nodeName is not null and j.status = 'COMPLETED' "
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

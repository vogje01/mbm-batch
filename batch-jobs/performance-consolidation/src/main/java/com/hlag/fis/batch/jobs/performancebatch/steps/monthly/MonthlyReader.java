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
                "a.nodeName, from_unixtime(floor((unix_timestamp(a.lastUpdate) / :interval)) * :interval) as lastUpdate, " +
                "avg(a.systemLoad), " +
                "avg(a.totalRealMemory), " +
                "avg(a.freeRealMemory), " +
                "avg(a.usedRealMemory), " +
                "avg(a.freeRealMemoryPct), " +
                "avg(a.usedRealMemoryPct), " +
                "avg(a.totalVirtMemory), " +
                "avg(a.freeVirtMemory), " +
                "avg(a.usedVirtMemory), " +
                "avg(a.freeVirtMemoryPct), " +
                "avg(a.usedVirtMemoryPct), " +
                "avg(a.totalSwap), " +
                "avg(a.freeSwap), " +
                "avg(a.usedSwap), " +
                "avg(a.freeSwapPct), " +
                "avg(a.usedSwapPct), " +
                "avg(a.jobCount), " +
                "avg(a.stepCount) " +
                "from AgentPerformance a " +
                "where type = 'WEEKLY' " +
                "group by a.nodeName, from_unixtime(floor((unix_timestamp(a.lastUpdate) / :interval)) * :interval) " +
                "order by lastUpdate";
        Map<String, Long> parameters = new HashMap<>();
        parameters.put("interval", 24 * 3600L);
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

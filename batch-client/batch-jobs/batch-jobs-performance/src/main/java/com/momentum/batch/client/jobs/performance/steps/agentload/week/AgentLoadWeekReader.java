package com.momentum.batch.client.jobs.performance.steps.agentload.week;

import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class AgentLoadWeekReader {

    @Value("${performance.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    AgentLoadWeekReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select b.qualifier, avg(b.value) "
                + "from BatchPerformance b "
                + "where b.metric = 'host.total.system.load' and b.timestamp >= :startDateTime and b.timestamp <= :endDateTime "
                + "group by b.qualifier";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startDateTime", Timestamp.valueOf(LocalDate.now().atStartOfDay().minusWeeks(1)));
        parameters.put("endDateTime", Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .timeout(900)
                .fetchSize(chunkSize)
                .build();
    }
}

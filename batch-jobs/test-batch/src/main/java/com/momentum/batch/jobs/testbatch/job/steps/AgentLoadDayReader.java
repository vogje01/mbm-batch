package com.momentum.batch.jobs.testbatch.job.steps;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class AgentLoadDayReader {

    @Value("${consolidation.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    AgentLoadDayReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        String queryString = "select b.qualifier, avg(b.value) "
                + "from BatchPerformance b "
                + "where b.type='RAW' and b.metric = 'host.total.system.load' and b.timestamp >= :startDateTime and b.timestamp <= :endDateTime "
                + "group by b.qualifier";
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay().minusDays(0);
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay().plusDays(1);
        Map<String, Timestamp> parameters = new HashMap<>();
        parameters.put("startDateTime", Timestamp.valueOf(startDateTime));
        parameters.put("endDateTime", Timestamp.valueOf(endDateTime));
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

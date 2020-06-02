package com.hlag.fis.batch.jobs.performancebatch.steps.agentload;

import com.hlag.fis.batch.reader.CursorReaderBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class AgentLoadReader {

    @Value("${consolidation.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    AgentLoadReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay().minusDays(1);
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        String queryString = "select a.nodeName, avg(a.systemLoad) "
                + "from AgentPerformance a "
                + "where a.lastUpdate >= :startDateTime and a.lastUpdate <= :endDateTime "
                + "group by a.nodeName";
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

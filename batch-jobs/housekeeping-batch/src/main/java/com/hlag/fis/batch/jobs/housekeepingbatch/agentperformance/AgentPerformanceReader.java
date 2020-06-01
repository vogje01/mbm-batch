package com.hlag.fis.batch.jobs.housekeepingbatch.agentperformance;

import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.logging.BatchLogging;
import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

@Component
public class AgentPerformanceReader {

    @BatchLogging(stepName = "Housekeeping Agent Performance")
    private static Logger logger = LoggerFactory.getLogger(AgentPerformanceReader.class);

    @Value("${houseKeeping.batch.agentPerformance.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.agentPerformance.houseKeepingDays}")
    private int houseKeepingDays;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    AgentPerformanceReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {

        Timestamp cutOff = DateTimeUtils.getCutOffTimestampMidnight(houseKeepingDays);
        logger.debug(format("Agent performance reader starting - cutOff: {1}", cutOff));

        String queryString = "select a from AgentPerformance a where a.type = :type and a.lastUpdate < :cutOff";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", AgentPerformanceType.ALL);
        parameters.put("cutOff", cutOff);
        return new CursorReaderBuilder<>(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

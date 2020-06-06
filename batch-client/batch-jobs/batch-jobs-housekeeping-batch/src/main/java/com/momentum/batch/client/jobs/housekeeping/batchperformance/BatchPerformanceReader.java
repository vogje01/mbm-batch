package com.momentum.batch.client.jobs.housekeeping.batchperformance;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import com.momentum.batch.domain.BatchPerformanceType;
import com.momentum.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

@Component
public class BatchPerformanceReader {

    @Value("${houseKeeping.batch.batchPerformance.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.batchPerformance.houseKeepingDays}")
    private int houseKeepingDays;

    private final BatchLogger logger;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    BatchPerformanceReader(BatchLogger logger, EntityManagerFactory mysqlEmf) {
        this.logger = logger;
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {

        Timestamp cutOff = DateTimeUtils.getCutOffTimestampMidnight(houseKeepingDays);
        logger.debug(format("Batch performance reader starting - cutOff: {0}", cutOff));

        String queryString = "select a from BatchPerformance a where a.type = :type and a.timestamp < :cutOff";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", BatchPerformanceType.RAW);
        parameters.put("cutOff", cutOff);
        return new CursorReaderBuilder<>(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

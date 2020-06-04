package com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance;

import com.hlag.fis.batch.domain.BatchPerformanceType;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.reader.CursorReaderBuilder;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @BatchStepLogger(value = "Housekeeping Batch Performance")
    private static Logger logger = LoggerFactory.getLogger(BatchPerformanceReader.class);

    @Value("${houseKeeping.batch.batchPerformance.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.batchPerformance.houseKeepingDays}")
    private int houseKeepingDays;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    BatchPerformanceReader(EntityManagerFactory mysqlEmf) {
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

package com.momentum.batch.client.jobs.housekeeping.jobexecution;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import com.momentum.batch.common.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

@Component
public class JobExecutionInfoReader {

    @Value("${houseKeeping.batch.jobExecutionInfo.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionInfo.houseKeepingDays}")
    private int houseKeepingDays;

    private final BatchLogger logger;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    JobExecutionInfoReader(BatchLogger logger, EntityManagerFactory mysqlEmf) {
        this.logger = logger;
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {

        Date cutOff = DateTimeUtils.getCutOffDate(houseKeepingDays);
        logger.debug(format("Job execution reader starting - cutOff: {0}", cutOff));

        String queryString = "select j from JobExecutionInfo j where j.lastUpdated < :cutOff";
        Map<String, Date> parameters = new HashMap<>();
        parameters.put("cutOff", cutOff);
        return new CursorReaderBuilder<>(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

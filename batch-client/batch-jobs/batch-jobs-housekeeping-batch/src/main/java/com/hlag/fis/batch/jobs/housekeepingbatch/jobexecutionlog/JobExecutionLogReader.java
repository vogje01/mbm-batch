package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog;

import com.momentum.batch.client.common.job.reader.CursorReaderBuilder;
import com.momentum.batch.client.common.logging.BatchLogger;
import com.momentum.batch.util.DateTimeUtils;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

@Component
public class JobExecutionLogReader {

    @Value("${houseKeeping.batch.jobExecutionLog.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionLog.houseKeepingDays}")
    private int houseKeepingDays;

    private final BatchLogger logger;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    JobExecutionLogReader(BatchLogger logger, EntityManagerFactory mysqlEmf) {
        this.logger = logger;
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {

        logger.debug(format("Job execution log reader starting - cutOff: {0}", DateTimeUtils.getCutOffDate(houseKeepingDays)));

        String queryString = "select j from JobExecutionLog j where j.timestamp < :cutOff";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cutOff", DateTimeUtils.getCutOffUnixtime(houseKeepingDays));
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

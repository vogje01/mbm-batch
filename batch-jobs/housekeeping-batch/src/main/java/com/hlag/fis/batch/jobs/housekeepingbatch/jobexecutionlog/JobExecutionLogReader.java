package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog;

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
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

@Component
public class JobExecutionLogReader {

    @BatchStepLogger(value = "Housekeeping JobExecutionLog")
    private static Logger logger = LoggerFactory.getLogger(JobExecutionLogReader.class);

    @Value("${houseKeeping.batch.jobExecutionLog.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionLog.houseKeepingDays}")
    private int houseKeepingDays;

    private final EntityManagerFactory mysqlEmf;

    @Autowired
    JobExecutionLogReader(EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {

        long cutOff = DateTimeUtils.getCutOffUnixtime(houseKeepingDays);
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

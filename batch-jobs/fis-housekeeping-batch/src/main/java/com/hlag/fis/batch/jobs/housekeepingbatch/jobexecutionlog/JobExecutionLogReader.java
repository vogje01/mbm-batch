package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog;

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
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

@Component
public class JobExecutionLogReader {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogReader.class);

    @Value("${houseKeeping.batch.jobExecutionLog.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionLog.houseKeepingDays}")
    private int houseKeepingDays;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    JobExecutionLogReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {

        long cutOff = DateTimeUtils.getCutOffUnixtime(houseKeepingDays);
        logger.debug(format("Job execution reader starting - cutOff: {1}", cutOff));

        String queryString = "select j from JobExecutionLog j where j.instant.epochSecond < :cutOff";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cutOff", DateTimeUtils.getCutOffUnixtime(houseKeepingDays));
        return new CursorReaderBuilder(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

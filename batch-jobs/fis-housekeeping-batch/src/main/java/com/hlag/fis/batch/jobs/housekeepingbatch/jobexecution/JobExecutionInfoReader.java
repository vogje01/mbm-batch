package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecution;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

@Component
public class JobExecutionInfoReader {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionInfoReader.class);

    @Value("${houseKeeping.batch.jobExecutionInfo.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionInfo.houseKeepingDays}")
    private int houseKeepingDays;

    private EntityManagerFactory mysqlEmf;

    @Autowired
    JobExecutionInfoReader(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEmf) {
        this.mysqlEmf = mysqlEmf;
    }

    ItemStreamReader getReader() {

        Date cutOff = DateTimeUtils.getCutOffDate(houseKeepingDays);
        logger.debug(format("Job execution reader starting - cutOff: {1}", cutOff));

        String queryString = "select j from JobExecutionInfo j where j.deleted = true or j.lastUpdated < :cutOff";
        Map<String, Date> parameters = new HashMap<>();
        parameters.put("cutOff", cutOff);
        return new CursorReaderBuilder<>(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }
}

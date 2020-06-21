package com.momentum.batch.client.jobs.housekeeping.steps;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import com.momentum.batch.common.util.DateTimeUtils;
import com.momentum.batch.server.database.domain.JobExecutionLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * Job execution info delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@Configuration
public class JobExecutionLogStep {

    @Value("${houseKeeping.batch.jobExecutionInfo.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionInfo.houseKeepingDays}")
    private int houseKeepingDays;

    private final EntityManagerFactory mysqlEmf;

    private final BatchLogger logger;

    @Autowired
    public JobExecutionLogStep(BatchLogger logger, EntityManagerFactory mysqlEmf) {
        this.logger = logger;
        this.mysqlEmf = mysqlEmf;
    }

    @Bean
    public ItemStreamReader<JobExecutionLog> jobExecutionInfoReader() {

        logger.debug(format("Job execution log reader starting - cutOff: {0}", DateTimeUtils.getCutOffDate(houseKeepingDays)));

        String queryString = "select j from JobExecutionLog j where j.timestamp < :cutOff";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cutOff", DateTimeUtils.getCutOffUnixtime(houseKeepingDays));
        return new CursorReaderBuilder<JobExecutionLog>(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }

    @Bean
    public ItemProcessor<JobExecutionLog, JobExecutionLog> jobExecutionInfoItemProcessor() {
        return jobExecutionLog -> jobExecutionLog;
    }

    @Bean
    public ItemWriter<JobExecutionLog> jobExecutionInfoWriter() {
        return list -> {

            // Get session
            Session session = mysqlEmf.unwrap(SessionFactory.class).openSession();
            session.getTransaction().begin();

            // Delete
            for (JobExecutionLog item : list) {
                item = session.get(JobExecutionLog.class, item.getId());
                session.delete(item);
            }

            // Commit
            session.flush();
            session.getTransaction().commit();
        };
    }
}

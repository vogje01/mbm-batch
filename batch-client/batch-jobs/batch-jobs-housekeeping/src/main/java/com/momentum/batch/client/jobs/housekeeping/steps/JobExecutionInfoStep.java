package com.momentum.batch.client.jobs.housekeeping.steps;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import com.momentum.batch.common.util.DateTimeUtils;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
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
import java.util.Date;
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
public class JobExecutionInfoStep {

    @Value("${houseKeeping.batch.jobExecutionInfo.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionInfo.houseKeepingDays}")
    private int houseKeepingDays;

    private final EntityManagerFactory mysqlEmf;

    private final BatchLogger logger;

    @Autowired
    public JobExecutionInfoStep(BatchLogger logger, EntityManagerFactory mysqlEmf) {
        this.logger = logger;
        this.mysqlEmf = mysqlEmf;
    }

    @Bean
    public ItemStreamReader<JobExecutionInfo> jobExecutionInfoReader() {

        Date cutOff = DateTimeUtils.getCutOffDate(houseKeepingDays);
        logger.debug(format("Job execution reader starting - cutOff: {0}", cutOff));

        String queryString = "select j from JobExecutionInfo j where j.lastUpdated < :cutOff";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cutOff", cutOff);
        return new CursorReaderBuilder<JobExecutionInfo>(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }

    @Bean
    public ItemProcessor<JobExecutionInfo, JobExecutionInfo> jobExecutionInfoItemProcessor() {
        return jobExecutionInfo -> jobExecutionInfo;
    }

    @Bean
    public ItemWriter<JobExecutionInfo> jobExecutionInfoWriter() {
        return list -> {

            // Get session
            Session session = mysqlEmf.unwrap(SessionFactory.class).openSession();
            session.getTransaction().begin();

            // Delete
            for (JobExecutionInfo item : list) {
                item = session.get(JobExecutionInfo.class, item.getId());
                session.delete(item.getJobExecutionContext());
                session.delete(item);
            }

            // Commit
            session.flush();
            session.getTransaction().commit();
        };
    }
}

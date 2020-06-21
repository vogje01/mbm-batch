package com.momentum.batch.client.jobs.housekeeping.steps;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import com.momentum.batch.common.util.DateTimeUtils;
import com.momentum.batch.server.database.domain.JobExecutionLog;
import com.momentum.batch.server.database.repository.JobExecutionLogRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Step;
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
public class JobExecutionLogStepConfiguration {

    @Value("${houseKeeping.batch.jobExecutionLog.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionLog.houseKeepingDays}")
    private int houseKeepingDays;

    private final EntityManagerFactory mysqlEmf;

    private final BatchLogger logger;

    private final BatchStepBuilder<JobExecutionLog, JobExecutionLog> stepBuilder;

    private final JobExecutionLogRepository jobExecutionLogRepository;

    @Autowired
    public JobExecutionLogStepConfiguration(BatchLogger logger, EntityManagerFactory mysqlEmf, BatchStepBuilder<JobExecutionLog, JobExecutionLog> stepBuilder,
                                            JobExecutionLogRepository jobExecutionLogRepository) {
        this.logger = logger;
        this.mysqlEmf = mysqlEmf;
        this.stepBuilder = stepBuilder;
        this.jobExecutionLogRepository = jobExecutionLogRepository;
    }

    @Bean("JobExecutionLog")
    public Step jobExecutionLogStep() {
        long totalCount = jobExecutionLogRepository.countByTimestamp(DateTimeUtils.getCutOffUnixtime(houseKeepingDays));
        return stepBuilder
                .name("Housekeeping job execution info")
                .chunkSize(chunkSize)
                .reader(jobExecutionLogReader())
                .processor(jobExecutionLogProcessor())
                .writer(jobExecutionLogWriter())
                .total(totalCount)
                .build();
    }

    @Bean
    public ItemStreamReader<JobExecutionLog> jobExecutionLogReader() {
        logger.debug(format("Job execution log reader starting - cutOff: {0}", DateTimeUtils.getCutOffUnixtime(houseKeepingDays)));
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
    public ItemProcessor<JobExecutionLog, JobExecutionLog> jobExecutionLogProcessor() {
        return jobExecutionLog -> jobExecutionLog;
    }

    @Bean
    public ItemWriter<JobExecutionLog> jobExecutionLogWriter() {
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

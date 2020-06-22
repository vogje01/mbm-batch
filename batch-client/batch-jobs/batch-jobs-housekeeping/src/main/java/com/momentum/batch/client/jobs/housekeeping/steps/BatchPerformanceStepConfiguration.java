package com.momentum.batch.client.jobs.housekeeping.steps;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.client.jobs.common.reader.CursorReaderBuilder;
import com.momentum.batch.common.util.DateTimeUtils;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.domain.BatchPerformanceType;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
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
import java.sql.Timestamp;
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
public class BatchPerformanceStepConfiguration {

    @Value("${houseKeeping.batch.batchPerformance.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.batchPerformance.houseKeepingDays}")
    private int houseKeepingDays;

    private final EntityManagerFactory mysqlEmf;

    private final BatchLogger logger;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public BatchPerformanceStepConfiguration(BatchLogger logger, EntityManagerFactory mysqlEmf, BatchPerformanceRepository batchPerformanceRepository,
                                             BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder) {
        this.logger = logger;
        this.mysqlEmf = mysqlEmf;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.stepBuilder = stepBuilder;
    }

    @Bean("BatchPerformance")
    public Step batchPerformanceStep() {
        Timestamp cutOff = DateTimeUtils.getCutOffTimestampMidnight(houseKeepingDays);
        long totalCount = batchPerformanceRepository.countByTimestamp(BatchPerformanceType.RAW, cutOff);
        return stepBuilder
                .name("Housekeeping batch performance")
                .chunkSize(chunkSize)
                .reader(batchPerformanceReader())
                .processor(batchPerformanceItemProcessor())
                .writer(batchPerformanceWriter())
                .total(totalCount)
                .build();
    }

    @Bean
    public ItemStreamReader<BatchPerformance> batchPerformanceReader() {
        Timestamp cutOff = DateTimeUtils.getCutOffTimestampMidnight(houseKeepingDays);
        logger.debug(format("Batch performance reader starting - cutOff: {0}", cutOff));
        String queryString = "select a from BatchPerformance a where a.type = :type and a.timestamp < :cutOff";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("type", BatchPerformanceType.RAW);
        parameters.put("cutOff", cutOff);
        return new CursorReaderBuilder<BatchPerformance>(mysqlEmf)
                .queryString(queryString)
                .parameters(parameters)
                .fetchSize(chunkSize)
                .build();
    }

    @Bean
    public ItemProcessor<BatchPerformance, BatchPerformance> batchPerformanceItemProcessor() {
        return batchPerformance -> batchPerformance;
    }

    @Bean
    public ItemWriter<BatchPerformance> batchPerformanceWriter() {
        return list -> {

            // Get session
            Session session = mysqlEmf.unwrap(SessionFactory.class).openSession();
            session.getTransaction().begin();

            // Delete
            for (BatchPerformance item : list) {
                item = session.get(BatchPerformance.class, item.getId());
                session.delete(item);
            }

            // Commit
            session.flush();
            session.getTransaction().commit();
        };
    }
}

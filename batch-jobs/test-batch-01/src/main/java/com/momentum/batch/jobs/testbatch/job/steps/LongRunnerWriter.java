package com.momentum.batch.jobs.testbatch.job.steps;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.logging.BatchStepLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Job execution info delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Component
public class LongRunnerWriter {

    @BatchStepLogger(value = "Long Runner")
    private static Logger logger = LoggerFactory.getLogger(LongRunnerReader.class);

    @SuppressWarnings("unchecked")
    public ItemWriter<User> getWriter() {
        return new ItemWriter<User>() {

            @Override
            public void write(List<? extends User> list) throws Exception {
                logger.debug(format("List written"));
            }
        };
    }
}

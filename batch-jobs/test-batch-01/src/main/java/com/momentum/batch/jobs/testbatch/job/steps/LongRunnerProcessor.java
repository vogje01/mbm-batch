package com.momentum.batch.jobs.testbatch.job.steps;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class LongRunnerProcessor implements ItemProcessor<User, User> {

    @BatchStepLogger(value = "Long runner")
    private static Logger logger = LoggerFactory.getLogger(LongRunnerProcessor.class);

    private final AgentRepository agentRepository;

    @Autowired
    public LongRunnerProcessor(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public User process(User user) {
        logger.trace(format("Processing item - user: {0}", user));
        return null;
    }
}

package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Component
public class AgentLoadDayProcessor implements ItemProcessor<Object[], Agent> {

    @BatchStepLogger(value = "Agent Load Day")
    private static Logger logger = LoggerFactory.getLogger(AgentLoadDayProcessor.class);

    private final AgentRepository agentRepository;

    @Autowired
    public AgentLoadDayProcessor(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1}", tuple[0], tuple[1]));
        // Check old record
        Optional<Agent> agentOptional = agentRepository.findByNodeName((String) tuple[0]);
        if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();
            agent.setAvgSystemLoadDay((Double) tuple[1]);
            return agent;
        }
        return null;
    }
}

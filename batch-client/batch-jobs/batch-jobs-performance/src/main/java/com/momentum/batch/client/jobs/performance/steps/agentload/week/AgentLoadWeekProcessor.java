package com.momentum.batch.client.jobs.performance.steps.agentload.week;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.repository.AgentRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Component
public class AgentLoadWeekProcessor implements ItemProcessor<Object[], Agent> {

    private final BatchLogger logger;

    private final AgentRepository agentRepository;

    @Autowired
    public AgentLoadWeekProcessor(BatchLogger logger, AgentRepository agentRepository) {
        this.logger = logger;
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1}", tuple[0], tuple[1]));
        Optional<Agent> agentOptional = agentRepository.findByNodeName((String) tuple[0]);
        if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();
            agent.setAvgSystemLoadWeek((double) tuple[1]);
            return agent;
        }
        return null;
    }
}

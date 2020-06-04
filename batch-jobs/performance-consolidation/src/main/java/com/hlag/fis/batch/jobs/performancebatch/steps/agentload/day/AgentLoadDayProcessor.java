package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentLoadDayProcessor implements ItemProcessor<Object[], BatchPerformance> {

    @BatchStepLogger(value = "Agent Load Day")
    private static Logger logger = LoggerFactory.getLogger(AgentLoadDayProcessor.class);

    private final AgentRepository agentRepository;

    @Autowired
    public AgentLoadDayProcessor(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public BatchPerformance process(Object[] tuple) {
        return new BatchPerformance((String) tuple[0], "node.load.day", (Double) tuple[1]);
    }
}

package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.week;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.logging.BatchStepLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentLoadWeekProcessor implements ItemProcessor<Object[], BatchPerformance> {

    @BatchStepLogger(value = "Job count")
    private static Logger logger = LoggerFactory.getLogger(AgentLoadWeekProcessor.class);

    @Autowired
    public AgentLoadWeekProcessor() {
    }

    @Override
    public BatchPerformance process(Object[] tuple) {
        return new BatchPerformance((String) tuple[0], "node.load.week", (Double) tuple[1]);
    }
}

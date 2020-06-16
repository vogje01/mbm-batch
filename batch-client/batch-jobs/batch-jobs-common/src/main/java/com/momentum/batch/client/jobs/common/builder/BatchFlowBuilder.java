package com.momentum.batch.client.jobs.common.builder;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * +---------------------------------------------------+
 * | +--------+                                        |
 * | |        |                                        |
 * | +--------+                                        |
 * +---------------------------------------------------+
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.3
 */
@Component
public class BatchFlowBuilder<T> {
    /**
     * Spring batch flow builder
     */
    private FlowBuilder<T> flowBuilder;
    /**
     * Task executor for parallel steps.
     */
    private TaskExecutor taskExecutor;
    /**
     * Name of the flow
     */
    private String flowName;
    /**
     * Start flag
     */
    private boolean start = true;
    /**
     * Size of steps and flows
     */
    private int size = 0;

    /**
     * Constructor.
     *
     * @param taskExecutor injected task executor.
     */
    @Autowired
    public BatchFlowBuilder(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public BatchFlowBuilder(String flowName) {
        this.flowName = flowName;
        flowBuilder = new FlowBuilder<>(flowName);
    }

    public BatchFlowBuilder<T> name(String flowName) {
        this.flowName = flowName;
        flowBuilder = new FlowBuilder<>(flowName);
        return this;
    }

    public BatchFlowBuilder<T> step(Step step) {
        if (step == null) {
            return null;
        }
        if (start) {
            flowBuilder.start((Flow) new FlowBuilder<>(step.getName()).from(step).build());
            start = false;
        } else {
            flowBuilder.next((Flow) new FlowBuilder<>(step.getName()).from(step).build());
        }
        size++;
        return this;
    }

    public BatchFlowBuilder<T> flow(Flow flow) {
        if (flow == null) {
            return null;
        }
        if (start) {
            startFlow(flow);
            start = false;
        } else {
            nextFlow(flow);
        }
        size++;
        return this;
    }

    public BatchFlowBuilder<T> startStep(Step step) {
        flowBuilder.start((Flow) new FlowBuilder<>(step.getName()).from(step).build());
        size++;
        return this;
    }

    public BatchFlowBuilder<T> startFlow(Flow flow) {
        if (flow == null) {
            return this;
        }
        flowBuilder.start(flow);
        size++;
        return this;
    }

    public BatchFlowBuilder<T> nextFlow(Flow flow) {
        if (flow == null) {
            return this;
        }
        flowBuilder.next(flow);
        size++;
        return this;
    }

    public BatchFlowBuilder<T> splitSteps(Step... splitSteps) {
        if (splitSteps.length == 0) {
            return null;
        }
        List<Flow> splitFlows = new ArrayList<>();
        for (Step s : splitSteps) {
            splitFlows.add(createFlow(s));
        }
        splitFlows(splitFlows);
        size += splitSteps.length;
        return this;
    }

    public BatchFlowBuilder<T> splitSteps(List<Step> splitSteps) {
        flowBuilder.next(new FlowBuilder<Flow>(flowName)
                .split(taskExecutor)
                .add(splitSteps.stream()
                        .map(this::createFlow)
                        .toArray(Flow[]::new))
                .build());
        size += splitSteps.size();
        return this;
    }

    public BatchFlowBuilder<T> splitFlows(Flow... splitFlows) {
        if (splitFlows.length == 0) {
            return null;
        }
        flowBuilder.next(new FlowBuilder<Flow>(flowName).split(taskExecutor).add(splitFlows).build());
        size += splitFlows.length;
        return this;
    }

    public BatchFlowBuilder<T> splitFlows(List<Flow> splitFlows) {
        if (splitFlows.isEmpty()) {
            return null;
        }
        flowBuilder.next(new FlowBuilder<Flow>(flowName).split(taskExecutor).add(splitFlows.toArray(new Flow[0])).build());
        return this;
    }

    public BatchFlowBuilder<T> conditionalFlow(String name, Step from, String exitCode, Step to) {
        flowBuilder.next(new FlowBuilder<Flow>(name).from(from).on(exitCode).to(to).build());
        return this;
    }

    public Flow build() {
        return (Flow) flowBuilder.build();
    }

    public int getSize() {
        return size;
    }

    /**
     * Create a new flow object from a step.
     *
     * @param step step to convert to a flow.
     * @return flow object.
     */
    private Flow createFlow(Step step) {
        return new FlowBuilder<Flow>(step.getName()).from(step).end();
    }
}

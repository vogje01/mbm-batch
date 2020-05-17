package com.hlag.fis.batch.jobs.housekeepingbatch.agentperformance;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.logging.BatchLogger;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.slf4j.Logger;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class AgentPerformanceStep {

	private static final String STEP_NAME = "Housekeeping Agent Performance";

	private static final Logger logger = BatchLogger.getStepLogger(STEP_NAME, AgentPerformanceStep.class);

	@Value("${houseKeeping.batch.agentPerformance.chunkSize}")
	private int chunkSize;

	@Value("${houseKeeping.batch.agentPerformance.houseKeepingDays}")
	private int houseKeepingDays;

	private AgentPerformanceRepository agentPerformanceRepository;

	private AgentPerformanceReader agentPerformanceReader;

	private AgentPerformanceProcessor agentPerformanceProcessor;

	private AgentPerformanceWriter agentPerformanceWriter;

	private BatchStepBuilder<AgentPerformance, AgentPerformance> stepBuilder;

	@Autowired
	public AgentPerformanceStep(
			BatchStepBuilder<AgentPerformance, AgentPerformance> stepBuilder,
			AgentPerformanceRepository agentPerformanceRepository,
			AgentPerformanceReader agentPerformanceReader,
			AgentPerformanceProcessor agentPerformanceProcessor,
			AgentPerformanceWriter agentPerformanceWriter) {
		this.stepBuilder = stepBuilder;
		this.agentPerformanceRepository = agentPerformanceRepository;
		this.agentPerformanceReader = agentPerformanceReader;
		this.agentPerformanceProcessor = agentPerformanceProcessor;
		this.agentPerformanceWriter = agentPerformanceWriter;
		logger.debug(format("Step initialized - name: {0}", STEP_NAME));
	}

	@SuppressWarnings("unchecked")
	public Step houseKeepingAgentPerformances() {
		long totalCount = agentPerformanceRepository.countByLastUpdated(AgentPerformanceType.ALL, DateTimeUtils.getCutOffTimestampMidnight(houseKeepingDays));
		logger.debug(format("Total count - count: {0}", totalCount));
		return stepBuilder
				.name(STEP_NAME)
				.chunkSize(chunkSize)
				.reader(agentPerformanceReader.getReader())
				.processor(agentPerformanceProcessor)
				.writer(agentPerformanceWriter.getWriter())
				.total(totalCount)
				.build();
	}
}

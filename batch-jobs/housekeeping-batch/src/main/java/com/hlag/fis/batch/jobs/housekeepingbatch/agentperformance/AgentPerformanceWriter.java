package com.hlag.fis.batch.jobs.housekeepingbatch.agentperformance;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.writer.MysqlDeleteWriter;
import org.springframework.stereotype.Component;

/**
 * Agent performance delete writer.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Component
public class AgentPerformanceWriter extends MysqlDeleteWriter<AgentPerformance> {
}

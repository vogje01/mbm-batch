package com.hlag.fis.batch.domain.dto;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
public enum ServerCommandType {
	START_JOB,
	STOP_JOB,
	RESTART_JOB,
	RESCHEDULE_JOB,
	REMOVE_JOB,
	KILL_JOB
}

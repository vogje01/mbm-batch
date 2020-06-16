package com.momentum.batch.common.domain;

/**
 * Job command types.
 * <p>
 * following agent commands are supported:
 * </p>
 * <ul>
 *     <li>NEW_NODE: a new container with an agent installed started</li>
 *     <li>SHOWDOWN_NODE: a container with an agent has been shutdown</li>
 *     <li>STOP_NODE: a container with an agent does not process any requests anymore</li>
 * </ul>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.3
 */
public enum JobStatusType {
    JOB_START,
    JOB_FINISHED,
    STEP_START,
    STEP_FINISHED,
    CHUNK_START,
    CHUNK_FINISHED,
    CHUNK_ERROR;
}

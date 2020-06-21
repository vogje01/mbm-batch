package com.momentum.batch.server.database.domain;

/**
 * Job schedule modes.
 * <p>
 * The following agent commands are supported:
 * </p>
 * <ul>
 *     <li>FIXED: job runs always on same node.</li>
 *     <li>RANDOM: job runs on a random node.</li>
 *     <li>RANDOM_GROUP: job runs on a random node of a agent group.</li>
 *     <li>MINIMUM_LOAD: job runs on node with the minimum load.</li>
 * </ul>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
public enum JobScheduleMode {
    FIXED,
    RANDOM,
    RANDOM_GROUP,
    MINIMUM_LOAD;
}

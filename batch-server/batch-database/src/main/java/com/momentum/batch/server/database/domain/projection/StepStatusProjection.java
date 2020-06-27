package com.momentum.batch.server.database.domain.projection;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public interface StepStatusProjection {

    String getStatus();

    Long getValue();
}

package com.hlag.fis.batch.jobs.housekeeping.steps.plannedshipment;

import com.hlag.fis.batch.writer.MysqlDeleteWriter;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import org.springframework.stereotype.Component;

/**
 * Planned shipment housekeeping writer.
 *
 * @author Jens.Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class PlannedShipmentWriter extends MysqlDeleteWriter<PlannedShipment> {
}

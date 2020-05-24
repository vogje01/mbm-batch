package com.hlag.fis.batch.jobs.housekeeping.steps.documentationlifecycle;

import com.hlag.fis.batch.writer.MysqlDeleteWriter;
import com.hlag.fis.db.mysql.model.DocumentationLifecycle;
import org.springframework.stereotype.Component;

@Component
public class DocumentationLifecycleWriter extends MysqlDeleteWriter<DocumentationLifecycle> {
}

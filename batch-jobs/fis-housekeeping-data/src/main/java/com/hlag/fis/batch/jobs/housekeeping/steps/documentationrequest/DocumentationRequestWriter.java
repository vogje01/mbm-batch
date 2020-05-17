package com.hlag.fis.batch.jobs.housekeeping.steps.documentationrequest;

import com.hlag.fis.batch.writer.MysqlDeleteWriter;
import com.hlag.fis.db.mysql.model.DocumentationRequest;
import org.springframework.stereotype.Component;

@Component
public class DocumentationRequestWriter extends MysqlDeleteWriter<DocumentationRequest> {

}

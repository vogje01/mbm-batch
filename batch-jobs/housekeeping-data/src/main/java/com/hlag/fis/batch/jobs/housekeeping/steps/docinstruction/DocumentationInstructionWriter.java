package com.hlag.fis.batch.jobs.housekeeping.steps.docinstruction;

import com.hlag.fis.batch.writer.MysqlDeleteWriter;
import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import org.springframework.stereotype.Component;

/**
 * Documentation instruction housekeeping writer.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@Component
public class DocumentationInstructionWriter extends MysqlDeleteWriter<DocumentationInstruction> {
}

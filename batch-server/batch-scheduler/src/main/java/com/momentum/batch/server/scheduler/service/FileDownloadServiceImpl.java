package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

import static java.text.MessageFormat.format;

/**
 * Batch job file download service.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Service
public class FileDownloadServiceImpl implements FileDownloadService {

    @Value("${mbm.library.root}")
    public String rootDirectory;

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    @Override
    public FileSystemResource download(String fileName) throws IOException {
        t.restart();
        // TODO: Check file existence
        logger.info(format("Sending job jar file to agent - fileName: {0}", fileName));
        FileSystemResource fileSystemResource = new FileSystemResource(Paths.get(jobsDirectory, fileName + ".jar"));
        logger.info(format("Sending job jar file to agent - fileName: {0} size: {1} {2}", fileName, fileSystemResource.contentLength(), t.elapsedStr()));
        return fileSystemResource;
    }
}

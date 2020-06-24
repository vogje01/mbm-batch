package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.util.MbmFileUtils;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
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
    public FileSystemResource download(String fileName) throws IOException, ResourceNotFoundException {
        t.restart();
        logger.info(format("Sending job jar file to agent - fileName: {0}", fileName));

        // Check file existence
        Path fullPath = Paths.get(jobsDirectory, fileName + ".jar");
        if (!MbmFileUtils.fileExists(fullPath.toString())) {
            logger.warn(format("File does not exist - fileName: {0}", fullPath));
            throw new ResourceNotFoundException("File does not exist");
        }

        // Send file to agent
        FileSystemResource fileSystemResource = new FileSystemResource(fullPath);
        logger.info(format("Sending job jar file to agent - fileName: {0} size: {1} {2}", fileName, fileSystemResource.contentLength(), t.elapsedStr()));

        return fileSystemResource;
    }
}

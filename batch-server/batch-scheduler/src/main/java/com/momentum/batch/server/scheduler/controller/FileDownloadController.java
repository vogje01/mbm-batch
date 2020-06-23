package com.momentum.batch.server.scheduler.controller;

import com.momentum.batch.common.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;

import static java.text.MessageFormat.format;

/**
 * Job file download controller.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/library/download")
public class FileDownloadController {

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private final MethodTimer t = new MethodTimer();

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public FileSystemResource download(@PathVariable("file_name") String fileName, HttpServletRequest request) {
        t.restart();

        logger.info(format("Sending job jar file to agent - fileName: {0} host: {1}", fileName, request.getRemoteHost()));
        FileSystemResource fileSystemResource = new FileSystemResource(Paths.get(jobsDirectory, fileName + ".jar"));
        logger.info(format("Sending job jar file to agent - fileName: {0} host: {1} {2}", fileName, request.getRemoteHost(), t.elapsedStr()));

        return fileSystemResource;
    }
}

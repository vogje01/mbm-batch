package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.util.FileUtils;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;

import static java.text.MessageFormat.format;

/**
 * Job file download service.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public FileSystemResource getFile(@PathVariable("file_name") String fileName, HttpServletRequest request) throws ResourceNotFoundException {
        logger.info(format("Starting job download request - fileName: {0} host: {1}", fileName, request.getRemoteHost()));
        if (FileUtils.exists(jobsDirectory + File.separator + fileName)) {
            throw new ResourceNotFoundException("File not found in jobs library");
        }
        logger.info(format("Sending job jar file to agent - fileName: {0} host: {1}", fileName, request.getRemoteHost()));
        return new FileSystemResource(Paths.get(jobsDirectory, fileName + ".jar"));
    }
}

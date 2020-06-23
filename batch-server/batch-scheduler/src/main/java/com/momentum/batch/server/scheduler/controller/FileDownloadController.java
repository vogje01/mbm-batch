package com.momentum.batch.server.scheduler.controller;

import com.momentum.batch.server.scheduler.service.FileDownloadService;
import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

    private final FileDownloadService fileDownloadService;

    /**
     * Constructor.
     *
     * @param fileDownloadService job file download service.
     */
    @Autowired
    public FileDownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public FileSystemResource download(@PathVariable("file_name") String fileName) throws ResourceNotFoundException, IOException {
        return fileDownloadService.download(fileName);
    }
}

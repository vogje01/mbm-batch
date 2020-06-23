package com.momentum.batch.server.scheduler.controller;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.scheduler.service.FileSystemService;
import com.momentum.batch.server.scheduler.util.FilePath;
import com.momentum.batch.server.scheduler.util.dto.FileSystemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Job file download controller.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/filesystem")
public class FileSystemController {

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private final MethodTimer t = new MethodTimer();

    private static final Logger logger = LoggerFactory.getLogger(FileSystemController.class);

    private final FileSystemService fileSystemService;

    /**
     * Constructor.
     *
     * @param fileSystemService file system server service.
     */
    @Autowired
    public FileSystemController(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @PutMapping(value = "/getItems", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<List<FileSystemDto>> getItems(@RequestBody FilePath filePath) {
        t.restart();
        logger.debug(format("File path request - path: {0}", filePath.getPath()));

        List<FileSystemDto> files = fileSystemService.getItems(filePath);
        return ResponseEntity.ok(files);
    }
}

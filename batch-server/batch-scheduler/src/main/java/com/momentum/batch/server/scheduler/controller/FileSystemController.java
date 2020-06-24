package com.momentum.batch.server.scheduler.controller;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.scheduler.service.FileSystemService;
import com.momentum.batch.server.scheduler.util.FilePath;
import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
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

import java.io.IOException;
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

    /**
     * Returns a list of file items.
     *
     * @param filePath root file path.
     * @return list of file system data transfer objects.
     */
    @PutMapping(value = "/getItems", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<List<FileSystemDto>> getItems(@RequestBody FilePath filePath) {
        t.restart();
        logger.debug(format("File path get items request - path: {0}", filePath.getPath()));

        List<FileSystemDto> files = fileSystemService.getItems(filePath);
        return ResponseEntity.ok(files);
    }

    /**
     * Delete a file system item.
     *
     * @param filePath file path to delete.
     * @return list of file system data transfer objects.
     */
    @PutMapping(value = "/deleteItem", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<List<FileSystemDto>> deleteItems(@RequestBody FilePath filePath) throws ResourceNotFoundException, IOException {
        t.restart();
        logger.debug(format("File path delete request - path: {0}", filePath.getPath()));

        fileSystemService.deleteItems(filePath);
        return ResponseEntity.ok().build();
    }
}

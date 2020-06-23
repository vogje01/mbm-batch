package com.momentum.batch.server.scheduler.library;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.dto.FileSystemDto;
import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.scheduler.service.JobFileUploadService;
import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
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
@RequestMapping("/api/library")
public class LibraryFileController {

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private final MethodTimer t = new MethodTimer();

    private static final Logger logger = LoggerFactory.getLogger(LibraryFileController.class);

    private final JobFileUploadService jobFileUploadService;

    /**
     * Constructor.
     *
     * @param jobFileUploadService job file upload service  avatar service.
     */
    @Autowired
    public LibraryFileController(JobFileUploadService jobFileUploadService) {
        this.jobFileUploadService = jobFileUploadService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FileSystemDto>> getPaths(@RequestParam String command, @RequestParam(required = false) String arguments) {
        t.restart();
        logger.debug(format("File path request . command: {0}", command));

        List<FileSystemDto> files = jobFileUploadService.getDirContents(new String[]{"."});
        return ResponseEntity.ok(files);
    }

    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public FileSystemResource download(@PathVariable("file_name") String fileName, HttpServletRequest request) {
        logger.info(format("Sending job jar file to agent - fileName: {0} host: {1}", fileName, request.getRemoteHost()));
        return new FileSystemResource(Paths.get(jobsDirectory, fileName + ".jar"));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<UserDto> upload(HttpServletRequest request) throws ResourceNotFoundException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile multipart = multipartRequest.getFile(it.next());

        jobFileUploadService.upload(multipart);

        return ResponseEntity.ok().build();
    }
}

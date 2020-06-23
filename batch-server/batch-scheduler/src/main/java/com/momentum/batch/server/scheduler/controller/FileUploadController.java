package com.momentum.batch.server.scheduler.controller;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.scheduler.service.JobFileUploadService;
import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

/**
 * Job file download controller.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/library/upload")
public class FileUploadController {

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private final MethodTimer t = new MethodTimer();

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    private final JobFileUploadService jobFileUploadService;

    /**
     * Constructor.
     *
     * @param jobFileUploadService job file upload service  avatar service.
     */
    @Autowired
    public FileUploadController(JobFileUploadService jobFileUploadService) {
        this.jobFileUploadService = jobFileUploadService;
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

package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.manager.service.JobFileUploadService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * User  REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobfileupload")
public class JobFileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(JobFileUploadController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobFileUploadService jobFileUploadService;

    /**
     * Constructor.
     *
     * @param jobFileUploadService job file upload service  avatar service.
     */
    @Autowired
    public JobFileUploadController(JobFileUploadService jobFileUploadService) {
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

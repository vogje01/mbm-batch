package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public interface JobFileUploadService {

    void upload(MultipartFile file) throws ResourceNotFoundException, IOException;
}

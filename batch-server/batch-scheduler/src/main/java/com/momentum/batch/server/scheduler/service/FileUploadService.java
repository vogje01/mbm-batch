package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
import com.momentum.batch.server.scheduler.util.dto.UploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public interface FileUploadService {

    void upload(MultipartFile file) throws ResourceNotFoundException, IOException;

    void uploadChunk(UploadDto uploadDto) throws IOException;
}

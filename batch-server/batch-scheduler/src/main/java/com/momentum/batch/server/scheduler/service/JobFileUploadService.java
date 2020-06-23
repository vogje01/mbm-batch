package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.server.database.domain.dto.FileSystemDto;
import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public interface JobFileUploadService {

    void upload(MultipartFile file) throws ResourceNotFoundException, IOException;

    List<FileSystemDto> getDirContents(String[] paths);
}

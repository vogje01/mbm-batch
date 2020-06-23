package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public interface FileDownloadService {

    FileSystemResource download(String file) throws ResourceNotFoundException, IOException;

}

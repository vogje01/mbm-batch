package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.server.database.domain.dto.FileSystemDto;
import com.momentum.batch.server.scheduler.util.FilePath;
import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;

import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public interface FileSystemService {

    List<FileSystemDto> getItems(FilePath paths);

    void deleteItems(FilePath paths) throws ResourceNotFoundException, IOException, BadRequestException;

}

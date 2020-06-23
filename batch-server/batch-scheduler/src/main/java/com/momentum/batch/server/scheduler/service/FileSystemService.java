package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.server.scheduler.util.FilePath;
import com.momentum.batch.server.scheduler.util.dto.FileSystemDto;

import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public interface FileSystemService {

    List<FileSystemDto> getItems(FilePath paths);

    void deleteItems(FilePath paths);

}

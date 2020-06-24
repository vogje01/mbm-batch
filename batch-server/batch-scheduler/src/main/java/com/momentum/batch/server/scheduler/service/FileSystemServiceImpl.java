package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.util.MbmFileUtils;
import com.momentum.batch.server.scheduler.util.FilePath;
import com.momentum.batch.server.scheduler.util.ResourceNotFoundException;
import com.momentum.batch.server.scheduler.util.dto.FileSystemDto;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Service
public class FileSystemServiceImpl implements FileSystemService {

    @Value("${mbm.library.root}")
    public String rootDirectory;

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private static final Logger logger = LoggerFactory.getLogger(FileSystemServiceImpl.class);

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    private FileSystemDto newDirectory(File file) {
        FileSystemDto fileSystemDto = new FileSystemDto();
        fileSystemDto.setIsDirectory(true);
        fileSystemDto.setName(file.getName());
        fileSystemDto.setHasSubDirectories(MbmFileUtils.hasSubDirectories(file));
        fileSystemDto.setDateModified(simpleDateFormat.format(file.lastModified()));
        logger.info(format("Added dir: name: {0}", file.getName()));
        return fileSystemDto;
    }

    private FileSystemDto newFile(File file) {
        FileSystemDto fileSystemDto = new FileSystemDto();
        fileSystemDto.setSize(FileUtils.sizeOf(file));
        fileSystemDto.setIsDirectory(false);
        fileSystemDto.setName(file.getName());
        fileSystemDto.setDateModified(simpleDateFormat.format(file.lastModified()));
        logger.info(format("Added file: name: {0}", file.getName()));
        return fileSystemDto;
    }

    @Override
    public List<FileSystemDto> getItems(FilePath filePath) {
        logger.info(format("Get dir content: name: {0}", filePath.getPath()));
        List<FileSystemDto> allFiles = new ArrayList<>();

        File root;
        FileSystemDto rootSystemDto;
        if (filePath.getPath() == null || filePath.getPath().isEmpty()) {
            root = new File(rootDirectory);
            rootSystemDto = newDirectory(root);
            allFiles.add(rootSystemDto);
            return allFiles;
        } else {
            String tmp = rootDirectory.substring(0, rootDirectory.lastIndexOf(File.separator));
            root = new File(tmp + File.separator + filePath.getPath());
            rootSystemDto = newFile(root);
        }

        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                rootSystemDto.addItem(newDirectory(file));
            } else {
                rootSystemDto.addItem(newFile(file));
            }
        }
        allFiles.addAll(rootSystemDto.getItems());
        return allFiles;
    }

    @Override
    public void deleteItems(FilePath filePath) throws ResourceNotFoundException, IOException, BadRequestException {
        logger.info(format("Delete items - name: {0}", filePath.getPath()));

        Path path = Path.of(rootDirectory.substring(0, rootDirectory.lastIndexOf(File.separator)), filePath.getPath());
        if (!filePath.getIsDirectory() && !MbmFileUtils.fileExists(path.toString())) {
            throw new ResourceNotFoundException("File not found");
        }
        if (filePath.getIsDirectory() && !MbmFileUtils.dirExists(path.toString())) {
            throw new ResourceNotFoundException("Directory not found");
        }
        if (!filePath.getIsDirectory() && MbmFileUtils.isLocked(path)) {
            throw new BadRequestException();
        }
        Files.delete(path);
    }
}

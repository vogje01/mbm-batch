package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.scheduler.util.FilePath;
import com.momentum.batch.server.scheduler.util.dto.FileSystemDto;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Batch job file upload.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Service
public class JobFileUploadServiceImpl implements JobFileUploadService {

    @Value("${mbm.library.root}")
    public String rootDirectory;

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private static final Logger logger = LoggerFactory.getLogger(JobFileUploadServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    @Autowired
    public JobFileUploadServiceImpl() {
    }

    @Override
    public void upload(MultipartFile file) throws IOException {
        t.restart();
        logger.info(format("Storing job file - name: {0} size: {1}", file.getOriginalFilename(), file.getSize()));

        // Write file to disk
        FileUtils.writeByteArrayToFile(new File(jobsDirectory + File.separator + file.getOriginalFilename()), file.getBytes());
        logger.info(format("Finished storing job file - name: {0} size: {1} {2}", file.getOriginalFilename(), file.getSize(), t.elapsedStr()));
    }

    private boolean hasSubdirs(File file) {
        if (file.listFiles() != null) {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    return true;
                }
            }
        }
        return false;
    }

    private FileSystemDto newDirectory(File file) {
        FileSystemDto fileSystemDto = new FileSystemDto();
        fileSystemDto.setIsDirectory(true);
        fileSystemDto.setName(file.getName());
        fileSystemDto.setHasSubDirectories(hasSubdirs(file));
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
    public List<FileSystemDto> getDirContents(FilePath filePath) {
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
}

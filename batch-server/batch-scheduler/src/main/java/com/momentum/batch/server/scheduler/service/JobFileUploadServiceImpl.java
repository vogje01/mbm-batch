package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.dto.FileSystemDto;
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
import java.util.Date;
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

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private static final Logger logger = LoggerFactory.getLogger(JobFileUploadServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

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

    @Override
    public List<FileSystemDto> getDirContents(String[] paths) {
        FileSystemDto file1 = new FileSystemDto();
        file1.setName("jobs");
        file1.setIsDirectory(true);
        file1.setSize(1024);
        file1.setDateModified(simpleDateFormat.format(new Date()));
        file1.setHasSubDirectories(false);

        FileSystemDto file2 = new FileSystemDto();
        file2.setName("file1.jar");
        file2.setIsDirectory(false);
        file2.setSize(1024);
        file2.setDateModified(simpleDateFormat.format(new Date()));
        file2.setHasSubDirectories(false);

        file1.addItem(file2);

        List<FileSystemDto> files = new ArrayList<>();
        files.add(file1);
        return files;
    }
}

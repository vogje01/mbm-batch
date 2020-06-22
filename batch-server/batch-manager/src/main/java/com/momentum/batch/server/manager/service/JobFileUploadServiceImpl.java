package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.util.MethodTimer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static java.text.MessageFormat.format;

/**
 * Batch job file upload.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Service
@Transactional
public class JobFileUploadServiceImpl implements JobFileUploadService {

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private static final Logger logger = LoggerFactory.getLogger(JobFileUploadServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

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
}

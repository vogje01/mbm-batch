package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.scheduler.util.dto.UploadDto;
import com.mysql.cj.util.Base64Decoder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${mbm.library.root}")
    public String rootDirectory;

    @Value("${mbm.library.jobs}")
    public String jobsDirectory;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    @Override
    public void upload(MultipartFile file) throws IOException {
        t.restart();
        logger.info(format("Storing job file - name: {0} size: {1}", file.getOriginalFilename(), file.getSize()));

        // Write file to disk
        FileUtils.writeByteArrayToFile(new File(jobsDirectory + File.separator + file.getOriginalFilename()), file.getBytes());
        logger.info(format("Finished storing job file - name: {0} size: {1} {2}", file.getOriginalFilename(), file.getSize(), t.elapsedStr()));
    }

    @Override
    public void uploadChunk(UploadDto uploadDto) throws IOException {
        logger.trace(format("Saving chunk - name: {0}", uploadDto.getFileName()));

        String relativeFileName = rootDirectory.substring(0, rootDirectory.lastIndexOf(File.separator)) + File.separator + uploadDto.getFileRelativePath();

        String encodeString = uploadDto.getFileData().substring(uploadDto.getFileData().indexOf(',') + 1);
        byte[] base64ByteArray = encodeString.getBytes();
        byte[] bytes = Base64Decoder.decode(base64ByteArray, 0, base64ByteArray.length);

        FileUtils.writeByteArrayToFile(new File(relativeFileName + File.separator + uploadDto.getFileName()), bytes, true);
        logger.trace(format("Chunk saving file finished - name: {0}", uploadDto.getFileName()));

    }
}

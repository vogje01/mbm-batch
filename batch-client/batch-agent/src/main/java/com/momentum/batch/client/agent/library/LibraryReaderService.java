package com.momentum.batch.client.agent.library;

import com.momentum.batch.common.domain.dto.JobDefinitionDto;
import com.momentum.batch.common.util.FileUtils;
import com.momentum.batch.common.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.NoSuchAlgorithmException;

import static java.text.MessageFormat.format;


/**
 * File downloader for job library files.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
@Component
public class LibraryReaderService {

    @Value("${mbm.library.directory}")
    private String libraryDirectory;

    private static final Logger logger = LoggerFactory.getLogger(LibraryReaderService.class);

    private final MethodTimer t = new MethodTimer();

    public void getJobFile(JobDefinitionDto jobDefinitonDto) throws IOException {

        t.restart();
        String fileName = jobDefinitonDto.getFileName();
        String jarFileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        logger.info(format("Downloading job library - name: {0}", jarFileName));

        if (checkHash(jarFileName, jobDefinitonDto.getFileHash())) {
            URL website = new URL("http://localhost:8090/api/library/" + jarFileName);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(libraryDirectory + File.separator + jarFileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            logger.info(format("Job library downloaded - name: {0} {1}", jarFileName, t.elapsedStr()));
        }
    }

    private boolean checkHash(String fileName, String currentHash) {
        /*if(currentHash == null) {
            return true;
        }*/
        try {
            long fileSize = FileUtils.getSize(libraryDirectory + File.separator + fileName);
            String fileHash = FileUtils.getHash(libraryDirectory + File.separator + fileName);
            return !currentHash.equals(fileHash);
        } catch (NoSuchAlgorithmException e) {
            logger.error(format("Could not find algorithm - error: {0}", e.getMessage()));
        } catch (IOException e) {
            logger.error(format("Could not read file - file: {0} error: {1}", fileName, e.getMessage()));
        }
        return true;
    }
}

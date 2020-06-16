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

    @Value("${mbm.library.jobs}")
    private String libraryDirectory;

    @Value("${mbm.library.server}")
    private String libraryServer;

    @Value("${mbm.library.port}")
    private int libraryPort;

    private static final Logger logger = LoggerFactory.getLogger(LibraryReaderService.class);

    private final MethodTimer t = new MethodTimer();

    /**
     * Download the job file from the server.
     *
     * @param jobDefinitionDto job definition data transfer object.
     * @throws IOException in case the file cannot be read.
     */
    public void getJobFile(JobDefinitionDto jobDefinitionDto) throws IOException {

        t.restart();
        String fileName = jobDefinitionDto.getFileName();
        String jarFileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        logger.info(format("Downloading job library - name: {0}", jarFileName));

        if (checkJobFile(jarFileName, jobDefinitionDto.getFileHash(), jobDefinitionDto.getFileSize())) {
            URL website = new URL(getServerUrl(jarFileName));
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(libraryDirectory + File.separator + jarFileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            logger.info(format("Job library downloaded - name: {0} {1}", jarFileName, t.elapsedStr()));
        }
    }

    /**
     * Checks the current local file against the file declared in the job definition.
     *
     * <p>
     * The job file needs to be downloaded, in case the file size has changed of the MD5 hash
     * is different from the job definition value.
     * </p>
     *
     * @param fileName    file name.
     * @param currentHash current MD5 hash.
     * @param currentSize current file size.
     * @return true when either the file size has changed of the MD5 hash.
     */
    private boolean checkJobFile(String fileName, String currentHash, long currentSize) {
        if (currentHash == null) {
            return true;
        }
        try {
            if (!FileUtils.exists(libraryDirectory + File.separator + fileName)) {
                return true;
            }
            long fileSize = FileUtils.getSize(libraryDirectory + File.separator + fileName);
            if (fileSize != currentSize) {
                return true;
            }
            String fileHash = FileUtils.getHash(libraryDirectory + File.separator + fileName);
            return !currentHash.equals(fileHash);
        } catch (NoSuchAlgorithmException e) {
            logger.error(format("Could not find algorithm - error: {0}", e.getMessage()));
        } catch (IOException e) {
            logger.error(format("Could not read file - file: {0} error: {1}", fileName, e.getMessage()));
        }
        return true;
    }

    private String getServerUrl(String fileName) {
        return "http://" + libraryServer + ":" + libraryPort + "/api/library/" + fileName;
    }
}

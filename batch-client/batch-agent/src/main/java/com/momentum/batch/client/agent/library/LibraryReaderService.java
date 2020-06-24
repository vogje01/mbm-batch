package com.momentum.batch.client.agent.library;

import com.momentum.batch.common.util.MbmFileUtils;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.text.MessageFormat.format;


/**
 * File downloader for job library files.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
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

    @Value("${mbm.library.user}")
    private String libraryUser;

    @Value("${mbm.library.password}")
    private String libraryPassword;

    private static final Logger logger = LoggerFactory.getLogger(LibraryReaderService.class);

    private final MethodTimer t = new MethodTimer();

    private final StringEncryptor stringEncryptor;

    public LibraryReaderService(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    /**
     * Download the job file from the server.
     * <p>
     * Basic authentication is used to authenticate to the server. User and password are taken
     * from the configuration file. The password is transferred encrypted.
     * </p>
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

            HttpURLConnection httpURLConnection = getHttpConnection(jarFileName);
            if (httpURLConnection != null) {

                // Connect to server
                httpURLConnection.connect();

                // Download file
                InputStream inputStream = httpURLConnection.getInputStream();
                ReadableByteChannel rbc = Channels.newChannel(inputStream);
                FileOutputStream fos = new FileOutputStream(libraryDirectory + File.separator + jarFileName);
                long size = fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

                // Check file
                if (size == jobDefinitionDto.getFileSize()) {
                    logger.info(format("Job library downloaded - name: {0} {1}", jarFileName, t.elapsedStr()));
                } else {
                    logger.warn(format("Job library size differ - name: {0} local: {1} jobDef: {2} {3}",
                            jarFileName, size, jobDefinitionDto.getFileSize(), t.elapsedStr()));
                }
            }
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
            if (!MbmFileUtils.fileExists(libraryDirectory + File.separator + fileName)) {
                return true;
            }
            long fileSize = MbmFileUtils.getSize(libraryDirectory + File.separator + fileName);
            if (fileSize != currentSize) {
                return true;
            }
            String fileHash = MbmFileUtils.getHash(libraryDirectory + File.separator + fileName);
            return !currentHash.equals(fileHash);
        } catch (IOException e) {
            logger.error(format("Could not read file - file: {0} error: {1}", fileName, e.getMessage()));
        }
        return true;
    }

    /**
     * Returns the server URL for the job file download.
     *
     * @param fileName file name.
     * @return URL for the job download.
     */
    private String getServerUrl(String fileName) {
        return "http://" + libraryServer + ":" + libraryPort + "/api/library/download/" + fileName;
    }

    /**
     * Returns a HTTP connection with basic authentication. User and password are taken from the
     * configuration file.
     *
     * @param jarFileName JAR file name.
     * @return HTTP connection with basic authentication header
     */
    private HttpURLConnection getHttpConnection(String jarFileName) {
        try {
            String url = getServerUrl(jarFileName);
            URL website = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) website.openConnection();
            logger.debug(format("Connection started - url: {0}", url));

            // Basic authentication
            String auth = libraryUser + ":" + stringEncryptor.encrypt(libraryPassword);
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
            httpConnection.setRequestProperty("Authorization", authHeaderValue);

            return httpConnection;
        } catch (MalformedURLException e) {
            logger.error(format("Malformed URL - file: {0} error: {2}", jarFileName, e.getMessage()));
        } catch (IOException e) {
            logger.error(format("Could not connect to server - file: {0} error: {2}", jarFileName, e.getMessage()));
        }
        return null;
    }
}

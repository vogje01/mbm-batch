package com.momentum.batch.client.agent.library;

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

    public void getJobFile(String fileName, long size) throws IOException {

        t.restart();

        String jarFileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        logger.info(format("Downloading job library - name: {0}", jarFileName));

        URL website = new URL("http://localhost:8090/api/library/" + jarFileName);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(libraryDirectory + File.separator + jarFileName);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        logger.info(format("Job library downloaded - name: {0} {1}", jarFileName, t.elapsedStr()));
    }

}

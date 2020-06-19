package com.momentum.batch.server.scheduler.configuration;

import com.momentum.batch.server.scheduler.library.LibraryFileWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class LibraryWatcherConfiguration {

    @Value("${mbm.library.dropins}")
    private String dropinsDirectory;

    @Autowired
    private LibraryFileWatcherService libraryFileWatcherService;

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LibraryWatcherConfiguration.class);

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher();
        fileSystemWatcher.addSourceFolder(new File(dropinsDirectory));
        fileSystemWatcher.addListener(libraryFileWatcherService);
        fileSystemWatcher.start();
        logger.info(format("File system watcher started - path: {0}", dropinsDirectory));
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() {
        fileSystemWatcher().stop();
    }
}

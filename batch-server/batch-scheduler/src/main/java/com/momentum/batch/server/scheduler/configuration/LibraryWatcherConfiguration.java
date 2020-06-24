package com.momentum.batch.server.scheduler.configuration;

import com.momentum.batch.common.util.filewatch.FileSystemWatcher;
import com.momentum.batch.server.scheduler.service.LibraryFileWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileFilter;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Configuration
public class LibraryWatcherConfiguration {

    @Value("${mbm.library.dropins}")
    private String dropinsDirectory;

    @Value("${mbm.library.jobs}")
    private String jobsDirectory;

    private final LibraryFileWatcherService libraryFileWatcherService;

    @Autowired
    public LibraryWatcherConfiguration(LibraryFileWatcherService libraryFileWatcherService) {
        this.libraryFileWatcherService = libraryFileWatcherService;
    }

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LibraryWatcherConfiguration.class);

    /**
     * File system watcher for jobs directories.
     *
     * <p>
     * Accepts only JAR files.
     * </p>
     *
     * @return File system watcher instance.
     */
    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileFilter fileFilter = pathname -> pathname.getName().endsWith(".jar");
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher();
        fileSystemWatcher.addSourceDirectory(new File(dropinsDirectory));
        fileSystemWatcher.addSourceDirectory(new File(jobsDirectory));
        fileSystemWatcher.setTriggerFilter(fileFilter);
        fileSystemWatcher.addListener(libraryFileWatcherService);
        fileSystemWatcher.start();
        logger.info(format("File system watcher started - jobs: {0} dropins: {1}", jobsDirectory, dropinsDirectory));
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() {
        fileSystemWatcher().stop();
    }
}

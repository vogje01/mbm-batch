package com.momentum.batch.server.scheduler.library;

import com.momentum.batch.common.util.FileUtils;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.sun.nio.file.ExtendedWatchEventModifier.FILE_TREE;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
@Service
public class LibraryFileWatcherService {

    @Value("${mbm.library.directory}")
    private String libraryDirectory;

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LibraryFileWatcherService.class);
    /**
     * Job definition repository.
     */
    private final JobDefinitionRepository jobDefinitionRepository;

    @Autowired
    public LibraryFileWatcherService(JobDefinitionRepository jobDefinitionRepository) {
        this.jobDefinitionRepository = jobDefinitionRepository;
    }

    @PostConstruct
    public void initialize() {
        try {
            Files.walk(Paths.get(libraryDirectory))
                    .filter(Files::isRegularFile)
                    .forEach(this::checkFile);
        } catch (IOException e) {
            logger.error(format("Could not scan directory - name: {0}", libraryDirectory));
        }
        startWatcher();
    }

    private void checkFile(Path file) {
        String fileName = file.toString();
        logger.debug(format("Checking file - path: {0}", file.toString()));
        try {
            Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findByName(file.toFile().getName());
            if (jobDefinitionOptional.isPresent()) {
                JobDefinition jobDefinition = jobDefinitionOptional.get();

                // Check file size

                // Check hash
                String currentHash = FileUtils.getHash(fileName);
                if (!currentHash.equals(jobDefinition.getFileHash())) {
                    long currentFileSize = FileUtils.getSize(fileName);
                    updateJobDefinition(jobDefinition, currentFileSize, currentHash);
                }
            }
        } catch (IOException ex) {
            logger.error(format("Could not get hash of file - name: {0}", fileName));
        } catch (NoSuchAlgorithmException ex) {
            logger.error(format("Could not get hash algorithm - name: MD5"));
        }
    }

    private void updateJobDefinition(JobDefinition jobDefinition, long fileSize, String fileHash) {
        jobDefinition.setFileSize(fileSize);
        jobDefinition.setFileHash(fileHash);
        jobDefinitionRepository.save(jobDefinition);
        logger.info(format("Job definition updated - name: {0}", jobDefinition.getName()));
    }

    private void startWatcher() {
        try {
            FileSystem fs = FileSystems.getDefault();
            WatchService ws = fs.newWatchService();
            Path pTemp = Paths.get(libraryDirectory);
            pTemp.register(ws, new WatchEvent.Kind[]{ENTRY_MODIFY, ENTRY_CREATE, ENTRY_DELETE}, FILE_TREE);
            while (true) {
                WatchKey watchKey = ws.take();
                for (WatchEvent<?> e : watchKey.pollEvents()) {
                    Path filename = (Path) e.context();
                    if (e.kind().equals(ENTRY_CREATE) || e.kind().equals(ENTRY_MODIFY)) {
                        checkFile(filename);
                    }
                }
                watchKey.reset();
            }
        } catch (IOException e) {
            logger.error(format("Could not start file watcher - directory: {0}", libraryDirectory));
        } catch (InterruptedException e) {
            logger.error(format("Watcher service interrupted - directory: {0}", libraryDirectory));
        }
    }
}

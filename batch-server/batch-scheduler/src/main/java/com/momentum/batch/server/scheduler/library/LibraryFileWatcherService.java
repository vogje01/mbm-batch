package com.momentum.batch.server.scheduler.library;

import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.util.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
@Component
public class LibraryFileWatcherService implements FileChangeListener {

    @Value("${mbm.library.dropins}")
    private String dropinsDirectory;

    @Value("${mbm.library.jobs}")
    private String jobsDirectory;

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
            Files.walk(Paths.get(jobsDirectory))
                    .filter(Files::isRegularFile)
                    .forEach(this::checkFile);
        } catch (IOException e) {
            logger.error(format("Could not scan directory - name: {0}", dropinsDirectory));
        }
    }

    private void checkFile(Path filePath) {
        logger.debug(format("Checking file - path: {0}", filePath.toString()));
        String fileName = filePath.toFile().getName();
        List<JobDefinition> jobDefinitionList = jobDefinitionRepository.findByFileName(fileName);
        if (jobDefinitionList.isEmpty()) {
            logger.info(format("File not found in job definition repository - name: {0}", fileName));
            createJobDefinition(fileName);
        } else {
            try {

                String currentHash = FileUtils.getHash(filePath.toString());
                long currentFileSize = FileUtils.getSize(filePath.toString());
                jobDefinitionList.forEach(jobDefinition -> {
                    // Check hash
                    if (!currentHash.equals(jobDefinition.getFileHash())) {
                        updateJobDefinition(jobDefinition, currentFileSize, currentHash);
                    }
                });
            } catch (IOException ex) {
                logger.error(format("Could not get hash of file - name: {0} error: {1}", filePath, ex.getMessage()));
            } catch (NoSuchAlgorithmException ex) {
                logger.error(format("Could not get hash algorithm - name: MD5 error: {0}", ex.getMessage()));
            }
        }
    }

    private void createJobDefinition(String fileName) {
        try {
            String currentHash = FileUtils.getHash(dropinsDirectory + File.separator + fileName);
            long currentFileSize = FileUtils.getSize(dropinsDirectory + File.separator + fileName);

            // Create new job definition
            String jobName = fileName.substring(0, fileName.lastIndexOf('-'));
            JobDefinition jobDefinition = new JobDefinition();
            jobDefinition.setFileSize(currentFileSize);
            jobDefinition.setFileHash(currentHash);
            jobDefinition.setName(jobName);
            jobDefinition.setFileName(fileName);
            jobDefinition.setType(FileUtils.getJobType(fileName));
            jobDefinition.setCommand("Command missing");
            jobDefinition.setWorkingDirectory("Working directory missing");
            jobDefinition.setLoggingDirectory("Logging directory missing");
            jobDefinition.setCompletedExitCode("0");
            jobDefinition.setCompletedExitMessage("Completed");
            jobDefinition.setFailedExitCode("-1");
            jobDefinition.setFailedExitMessage("Failed");
            jobDefinition.setLabel("New job definition: " + jobName);
            jobDefinition.setDescription("New job definition: " + jobName);
            jobDefinition.setJobVersion(FileUtils.getVersion(fileName));
            jobDefinition.setActive(false);

            // Save to database
            jobDefinitionRepository.save(jobDefinition);
            logger.info(format("Job definition created - name: {0} size: {1} hash: {2}", jobDefinition.getName(), currentFileSize, currentHash));

            // Move file from dropins to jobs directory.
            Files.move(new File(dropinsDirectory + File.separator + fileName).toPath(), new File(jobsDirectory + File.separator + fileName).toPath());

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
        logger.info(format("Job definition updated - name: {0} size: {1} hash: {2}", jobDefinition.getName(), fileSize, fileHash));
    }

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for (ChangedFiles cfiles : changeSet) {
            for (ChangedFile cfile : cfiles.getFiles()) {
                if ((cfile.getType().equals(ChangedFile.Type.MODIFY) || cfile.getType().equals(ChangedFile.Type.ADD)) && !isLocked(cfile.getFile().toPath())) {
                    checkFile(cfile.getFile().toPath());
                }
            }
        }
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }

}

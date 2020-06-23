package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.util.FileUtils;
import com.momentum.batch.common.util.filewatch.ChangedFile;
import com.momentum.batch.common.util.filewatch.ChangedFiles;
import com.momentum.batch.common.util.filewatch.FileChangeListener;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.repository.JobGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.4
 */
@Service
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
    /**
     * Job group repository.
     */
    private final JobGroupRepository jobGroupRepository;

    @Autowired
    public LibraryFileWatcherService(JobDefinitionRepository jobDefinitionRepository, JobGroupRepository jobGroupRepository) {
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.jobGroupRepository = jobGroupRepository;
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
            //createJobDefinition(filePath);
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
            }
        }

        // Move file from dropins to jobs directory.
        if (filePath.startsWith(dropinsDirectory)) {
            try {
                String src = dropinsDirectory + File.separator + fileName;
                String dst = jobsDirectory + File.separator + fileName;
                Files.move(new File(src).toPath(), new File(dst).toPath(), StandardCopyOption.REPLACE_EXISTING);
                logger.info(format("File moved - src: {0} dst: {1}", src, dst));
            } catch (IOException ex) {
                logger.error(format("Could not move file - name: {0} error: {1}", filePath, ex.getMessage()));
            }
        }
    }

    private void createJobDefinition(Path filePath) {
        try {
            String currentHash = FileUtils.getHash(filePath.toString());
            long currentFileSize = FileUtils.getSize(filePath.toString());

            // Create new job definition
            String fileName = filePath.toFile().getName();
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
            jobDefinition = jobDefinitionRepository.save(jobDefinition);
            logger.info(format("Job definition created - name: {0} size: {1} hash: {2}", jobDefinition.getName(), currentFileSize, currentHash));

            // Set default job group
            Optional<JobGroup> defaultGroup = jobGroupRepository.findByName("Default");
            if (defaultGroup.isPresent()) {
                jobDefinition.setJobMainGroup(defaultGroup.get());
                jobDefinitionRepository.save(jobDefinition);
            }
        } catch (IOException ex) {
            logger.error(format("Could not get hash of file - name: {0} error: {1}", filePath, ex.getMessage()));
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

package com.momentum.batch.common.util;

import com.momentum.batch.common.util.md5.MD5;
import com.momentum.batch.server.database.domain.JobDefinitionType;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File utilities.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.4
 */
public class MbmFileUtils {

    /**
     * Calculates and returns the MD5 hash of a file.
     *
     * @param fileName fully qualified file name.
     * @return MD5 hash as string.
     * @throws IOException in case the file cannot be read.
     */
    public static String getHash(String fileName) throws IOException {
        return MD5.asHex(MD5.getHash(new File(fileName)));
    }

    /**
     * Returns the file size in bytes.
     *
     * @param fileName fully qualified file name.
     * @return file size in bytes.
     */
    public static long getSize(String fileName) {
        return (new File(fileName)).length();
    }

    /**
     * Check the existence of a file.
     *
     * @param fileName fully qualified file name.
     * @return true in case file exists and is not a directory, otherwise false.
     */
    public static boolean fileExists(String fileName) {
        File f = new File(fileName);
        return f.exists() && !f.isDirectory();
    }

    /**
     * Check the existence of a directory.
     *
     * @param fileName fully qualified file name.
     * @return true in case file exists and is not a directory, otherwise false.
     */
    public static boolean dirExists(String fileName) {
        File f = new File(fileName);
        return f.exists() && f.isDirectory();
    }

    /**
     * Extract the version string from a filename.
     *
     * @param fileName file name.
     * @return version string or 0.0.0
     */
    public static String getVersion(String fileName) {
        Matcher m = Pattern.compile("batch-jobs-.*-(\\d+\\.\\d+\\.\\d+)(-.*)?\\..*").matcher(fileName);
        if (m.matches()) {
            if (m.group(2) != null) {
                return m.group(1) + m.group(2);
            }
            return m.group(1);
        }
        return "0.0.0";
    }

    /**
     * Get job definition type from file name.
     *
     * @param fileName file name.
     * @return JAR, SCRIPT or DOCKER.
     */
    public static JobDefinitionType getJobType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        if (extension.equals(".jar")) {
            return JobDefinitionType.JAR;
        }
        if (extension.isEmpty() && fileName.contains(":")) {
            return JobDefinitionType.DOCKER;
        }
        if (extension.equals(".sh") || extension.equals(".cmd") || extension.equals(".bat")) {
            return JobDefinitionType.SCRIPT;
        }
        return JobDefinitionType.UNKNOWN;
    }

    /**
     * Checks whether a given directory has sub-directories.
     *
     * @param directory directory to check
     * @return true if the given directory has sub-directories.
     */
    public static boolean hasSubDirectories(File directory) {
        if (directory.listFiles() != null) {
            for (File f : directory.listFiles()) {
                if (f.isDirectory()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check lock of file.
     *
     * @param path path to file.
     * @return true if file is locked, otherwise false.
     */
    public static boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }
}

package com.momentum.batch.common.util;

import com.momentum.batch.common.domain.JobDefinitionType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File utilities.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.4
 */
public class FileUtils {

    /**
     * Calculates and returns the MD5 hash of a file.
     *
     * @param fileName fully qualified file name.
     * @return MD5 hash as string.
     * @throws NoSuchAlgorithmException when the MD5 algorithm is not found.
     * @throws IOException              in case the file cannot be read.
     */
    public static String getHash(String fileName) throws NoSuchAlgorithmException, IOException {
        InputStream fis = new FileInputStream(fileName);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return String.format("%032X", new BigInteger(1, complete.digest()));
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
    public static boolean exists(String fileName) {
        File f = new File(fileName);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    /**
     * Extract the version string from a filename.
     *
     * @param fileName file name.
     * @return version string or 0.0.0
     */
    public static String getVersion(String fileName) {
        Matcher m = Pattern.compile("batch-jobs-.*-(\\d+\\.\\d+\\.\\d+-RELEASE|SNAPSHOT)\\..*").matcher(fileName);
        if (m.matches()) {
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
        return JobDefinitionType.UNKNOWN;
    }
}

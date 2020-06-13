package com.momentum.batch.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * File utilities.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
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
}

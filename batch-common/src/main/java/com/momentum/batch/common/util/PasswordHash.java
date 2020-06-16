package com.momentum.batch.common.util;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Password hashing.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
public class PasswordHash {

    public static String encryptPassword(String password) {
        return new StrongPasswordEncryptor().encryptPassword(password);
    }
}

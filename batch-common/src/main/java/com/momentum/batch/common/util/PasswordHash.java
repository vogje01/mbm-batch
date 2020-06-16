package com.momentum.batch.common.util;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Password hashing.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@Component
public class PasswordHash {

    private StringEncryptor stringEncryptor;

    @Autowired
    public PasswordHash(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    public String encryptPassword(String password) {
        return stringEncryptor.encrypt(password);
    }

    public String decryptPassword(String password) {
        return stringEncryptor.decrypt(password);
    }
}

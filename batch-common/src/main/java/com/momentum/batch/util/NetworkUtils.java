package com.momentum.batch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

import static java.net.InetAddress.getLocalHost;
import static java.text.MessageFormat.format;

/**
 * Network utilities.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
public class NetworkUtils {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

    public static String getHostName() {
        try {
            return getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            logger.error(format("Could not get local host name - error: {0}", ex.getMessage()), ex);
        }
        return null;
    }
}

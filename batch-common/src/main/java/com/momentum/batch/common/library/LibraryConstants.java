package com.momentum.batch.common.library;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
public class LibraryConstants {

    /**
     * Instantiation not allowed message
     */
    public static final String INSTANTIATION_NOT_ALLOWED = "Instantiation not allowed";
    /**
     * Maximal transfer size in bytes (default: 1GB)
     */
    public static final long TRANSFER_MAX_SIZE = (1024 * 1024 * 1024);
    /**
     * Buffer size in bytes (default: 2048)
     */
    public static final int BUFFER_SIZE = 2048;
    /**
     * Transfer message end marker
     */
    public static final String END_MESSAGE_MARKER = ":END";
    /**
     * Message delimiter
     */
    public static final String MESSAGE_DELIMITTER = "#";
    /**
     * Confirmation message.
     */
    public static final String CONFIRMATION = "OK";

    private LibraryConstants() {
        throw new IllegalStateException(INSTANTIATION_NOT_ALLOWED);
    }
}

package com.momentum.batch.common.library;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
public class LibraryMetaData {

    private final String fileName;
    private final long size;

    static LibraryMetaData from(final String request) {
        assert StringUtils.isNotEmpty(request);

        final String[] contents = request.replace(LibraryConstants.END_MESSAGE_MARKER, StringUtils.EMPTY).split(LibraryConstants.MESSAGE_DELIMITTER);
        return new LibraryMetaData(contents[0], Long.valueOf(contents[1]));
    }

    private LibraryMetaData(final String fileName, final long size) {
        assert StringUtils.isNotEmpty(fileName);

        this.fileName = fileName;
        this.size = size;
    }

    String getFileName() {
        return this.fileName;
    }

    long getSize() {
        return this.size;
    }
}

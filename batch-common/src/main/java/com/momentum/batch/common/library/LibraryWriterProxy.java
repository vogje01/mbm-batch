package com.momentum.batch.common.library;

import io.micrometer.core.instrument.util.StringUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
public class LibraryWriterProxy {

    private final LibraryWriter fileWriter;
    private final AtomicLong position;
    private final long size;
    private final String fileName;

    LibraryWriterProxy(final String path, final LibraryMetaData metaData) throws IOException {
        assert !Objects.isNull(metaData) && StringUtils.isNotEmpty(path);

        this.fileWriter = new LibraryWriter(path + "/" + metaData.getFileName());
        this.position = new AtomicLong(0l);
        this.size = metaData.getSize();
        this.fileName = metaData.getFileName();
    }

    String getFileName() {
        return this.fileName;
    }

    LibraryWriter getFileWriter() {
        return this.fileWriter;
    }

    AtomicLong getPosition() {
        return this.position;
    }

    boolean done() {
        return this.position.get() == this.size;
    }
}

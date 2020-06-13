package com.momentum.batch.common.library;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * Library reader for the JAR file sender.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
public class LibraryReader {

    @Value("${mbm.fileStore.directory}")
    private String fileStoreDirectory;

    private final FileChannel channel;
    private final LibrarySender sender;

    public LibraryReader(final LibrarySender sender, final String name) throws IOException {
        if (Objects.isNull(sender) || StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Sender and name required");
        }

        this.sender = sender;
        this.channel = FileChannel.open(Paths.get(fileStoreDirectory, name), StandardOpenOption.READ);
    }

    public void read() throws IOException {
        try {
            transfer();
        } finally {
            close();
        }
    }

    public void close() throws IOException {
        this.sender.close();
        this.channel.close();
    }

    private void transfer() throws IOException {
        this.sender.transfer(this.channel, 0l, this.channel.size());
    }
}

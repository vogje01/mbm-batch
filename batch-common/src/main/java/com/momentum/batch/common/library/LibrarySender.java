package com.momentum.batch.common.library;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 * Sends a batch job JAR file via a NIO file channel.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
public class LibrarySender {

    private final InetSocketAddress hostAddress;
    private SocketChannel client;

    public LibrarySender(final int port) throws IOException {
        this.hostAddress = new InetSocketAddress(port);
        this.client = SocketChannel.open(this.hostAddress);
    }

    void transfer(final FileChannel channel, long position, long size) throws IOException {
        assert !Objects.isNull(channel);

        while (position < size) {
            position += channel.transferTo(position, LibraryConstants.TRANSFER_MAX_SIZE, this.client);
        }
    }

    public SocketChannel getChannel() {
        return this.client;
    }

    void close() throws IOException {
        this.client.close();
    }
}


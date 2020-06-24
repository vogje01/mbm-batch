package com.momentum.batch.common.util.md5;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class MD5InputStream extends FilterInputStream {
    /**
     * MD5 context
     */
    private MD5 md5;

    /**
     * Creates a MD5InputStream
     *
     * @param in The input stream
     */
    public MD5InputStream(InputStream in) {
        super(in);

        md5 = new MD5();
    }

    /**
     * Read a byte of data.
     *
     * @see java.io.FilterInputStream
     */
    public int read() throws IOException {
        int c = in.read();

        if (c == -1)
            return -1;

        if ((c & ~0xff) != 0) {
            System.out.println("MD5InputStream.read() got character with (c & ~0xff) != 0)!");
        } else {
            md5.Update(c);
        }

        return c;
    }

    /**
     * Reads into an array of bytes.
     *
     * @see java.io.FilterInputStream
     */
    public int read(byte bytes[], int offset, int length) throws IOException {
        int r;

        if ((r = in.read(bytes, offset, length)) == -1)
            return r;

        md5.Update(bytes, offset, r);

        return r;
    }

    /**
     * Returns array of bytes representing hash of the stream as
     * finalized for the current state.
     *
     * @see MD5#Final
     */
    public byte[] hash() {
        return md5.Final();
    }

    public MD5 getMD5() {
        return md5;
    }
}


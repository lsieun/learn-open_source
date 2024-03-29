package lsieun.flink.api.common.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.fs.FSDataInputStream;

/**
 * This class wraps an {@link java.io.InputStream} and exposes it as {@link lsieun.flink.core.fs.FSDataInputStream}.
 * <br>
 * <i>NB: {@link #seek(long)} and {@link #getPos()} are currently not supported.</i>
 */
@Public
public class InputStreamFSInputWrapper extends FSDataInputStream {

    private final InputStream inStream;

    private long pos = 0;

    public InputStreamFSInputWrapper(InputStream inStream) {
        this.inStream = inStream;
    }

    @Override
    public void close() throws IOException {
        this.inStream.close();
    }

    @Override
    public void seek(long desired) throws IOException {
        if (desired < this.pos) {
            throw new IllegalArgumentException("Wrapped InputStream: cannot search backwards.");
        }

        while (this.pos < desired) {
            long numReadBytes = this.inStream.skip(desired - pos);
            if (numReadBytes == -1) {
                throw new EOFException("Unexpected EOF during forward seek.");
            }
            this.pos += numReadBytes;
        }
    }

    @Override
    public long getPos() throws IOException {
        return this.pos;
    }

    @Override
    public int read() throws IOException {
        int read = inStream.read();
        if (read != -1) {
            this.pos++;
        }
        return read;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int numReadBytes = inStream.read(b, off, len);
        if (numReadBytes != -1) {
            this.pos += numReadBytes;
        }
        return numReadBytes;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }
}

package lsieun.flink.core.fs.local;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.annotation.Nonnull;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.fs.FSDataInputStream;

/**
 * The <code>LocalDataInputStream</code> class is a wrapper class for a data
 * input stream to the local file system.
 */
@Internal
public class LocalDataInputStream extends FSDataInputStream {

    /** The file input stream used to read data from.*/
    private final FileInputStream fis;
    private final FileChannel fileChannel;

    /**
     * Constructs a new <code>LocalDataInputStream</code> object from a given {@link File} object.
     *
     * @param file The File the data stream is read from
     *
     * @throws IOException Thrown if the data input stream cannot be created.
     */
    public LocalDataInputStream(File file) throws IOException {
        this.fis = new FileInputStream(file);
        this.fileChannel = fis.getChannel();
    }

    @Override
    public void seek(long desired) throws IOException {
        if (desired != getPos()) {
            this.fileChannel.position(desired);
        }
    }

    @Override
    public long getPos() throws IOException {
        return this.fileChannel.position();
    }

    @Override
    public int read() throws IOException {
        return this.fis.read();
    }

    @Override
    public int read(@Nonnull byte[] buffer, int offset, int length) throws IOException {
        return this.fis.read(buffer, offset, length);
    }

    @Override
    public void close() throws IOException {
        // According to javadoc, this also closes the channel
        this.fis.close();
    }

    @Override
    public int available() throws IOException {
        return this.fis.available();
    }

    @Override
    public long skip(final long n) throws IOException {
        return this.fis.skip(n);
    }
}

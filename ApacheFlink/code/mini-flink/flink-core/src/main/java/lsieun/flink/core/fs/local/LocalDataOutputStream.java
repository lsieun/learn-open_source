package lsieun.flink.core.fs.local;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.fs.FSDataOutputStream;

/**
 * The <code>LocalDataOutputStream</code> class is a wrapper class for a data
 * output stream to the local file system.
 */
@Internal
public class LocalDataOutputStream extends FSDataOutputStream {

    /** The file output stream used to write data.*/
    private final FileOutputStream fos;

    /**
     * Constructs a new <code>LocalDataOutputStream</code> object from a given {@link File} object.
     *
     * @param file
     *        the {@link File} object the data stream is read from
     * @throws IOException
     *         thrown if the data output stream cannot be created
     */
    public LocalDataOutputStream(final File file) throws IOException {
        this.fos = new FileOutputStream(file);
    }

    @Override
    public void write(final int b) throws IOException {
        fos.write(b);
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        fos.write(b, off, len);
    }

    @Override
    public void close() throws IOException {
        fos.close();
    }

    @Override
    public void flush() throws IOException {
        fos.flush();
    }

    @Override
    public void sync() throws IOException {
        fos.getFD().sync();
    }

    @Override
    public long getPos() throws IOException {
        return fos.getChannel().position();
    }
}

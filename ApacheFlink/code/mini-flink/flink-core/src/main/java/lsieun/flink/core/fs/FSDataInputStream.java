package lsieun.flink.core.fs;


import java.io.IOException;
import java.io.InputStream;

import lsieun.flink.annotation.Public;

/**
 * Interface for a data input stream to a file on a {@link FileSystem}.
 *
 * <p>This extends the {@link java.io.InputStream} with methods for accessing
 * the stream's {@link #getPos() current position} and
 * {@link #seek(long) seeking} to a desired position.
 */
@Public
public abstract class FSDataInputStream extends InputStream {

    /**
     * Seek to the given offset from the start of the file. The next read() will be from that location.
     * Can't seek past the end of the stream.
     *
     * @param desired
     *        the desired offset
     * @throws IOException Thrown if an error occurred while seeking inside the input stream.
     */
    public abstract void seek(long desired) throws IOException;

    /**
     * Gets the current position in the input stream.
     *
     * @return current position in the input stream
     * @throws IOException Thrown if an I/O error occurred in the underlying stream
     *                     implementation while accessing the stream's position.
     */
    public abstract long getPos() throws IOException;
}

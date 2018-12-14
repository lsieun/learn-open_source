package lsieun.flink.core.fs;


import java.io.IOException;
import java.io.OutputStream;

import lsieun.flink.annotation.Public;

/**
 * An output stream to a file that is created via a {@link FileSystem}.
 * This class extends the base {@link java.io.OutputStream} with some additional important methods.
 *
 * <h2>Data Persistence Guarantees</h2>
 *
 * <p>These streams are used to persistently store data, both for results of streaming applications
 * and for fault tolerance and recovery. It is therefore crucial that the persistence semantics
 * of these streams are well defined.
 *
 * <p>Please refer to the class-level docs of {@link FileSystem} for the definition of data persistence
 * via Flink's FileSystem abstraction and the {@code FSDataOutputStream}.
 *
 * <h2>Thread Safety</h2>
 *
 * <p>Implementations of the {@code FSDataOutputStream} are generally not assumed to be thread safe.
 * Instances of {@code FSDataOutputStream} should not be passed between threads, because there
 * are no guarantees about the order of visibility of operations across threads.
 *
 * @see FileSystem
 * @see FSDataInputStream
 */
@Public
public abstract class FSDataOutputStream extends OutputStream {

    /**
     * Gets the position of the stream (non-negative), defined as the number of bytes
     * from the beginning of the file to the current writing position. The position
     * corresponds to the zero-based index of the next byte that will be written.
     *
     * <p>This method must report accurately report the current position of the stream.
     * Various components of the high-availability and recovery logic rely on the accurate
     *
     * @return The current position in the stream, defined as the number of bytes
     *         from the beginning of the file to the current writing position.
     *
     * @throws IOException Thrown if an I/O error occurs while obtaining the position from
     *                     the stream implementation.
     */
    public abstract long getPos() throws IOException;

    /**
     * Flushes the stream, writing any data currently buffered in stream implementation
     * to the proper output stream. After this method has been called, the stream implementation
     * must not hold onto any buffered data any more.
     *
     * <p>A completed flush does not mean that the data is necessarily persistent. Data
     * persistence can is only assumed after calls to {@link #close()} or {@link #sync()}.
     *
     * <p>Implementation note: This overrides the method defined in {@link OutputStream}
     * as abstract to force implementations of the {@code FSDataOutputStream} to implement
     * this method directly.
     *
     * @throws IOException Thrown if an I/O error occurs while flushing the stream.
     */
    public abstract void flush() throws IOException;

    /**
     * Flushes the data all the way to the persistent non-volatile storage (for example disks).
     * The method behaves similar to the <i>fsync</i> function, forcing all data to
     * be persistent on the devices.
     *
     * @throws IOException Thrown if an I/O error occurs
     */
    public abstract void sync() throws IOException;

    /**
     * Closes the output stream. After this method returns, the implementation must guarantee
     * that all data written to the stream is persistent/visible, as defined in the
     * {@link FileSystem class-level docs}.
     *
     * <p>The above implies that the method must block until persistence can be guaranteed.
     * For example for distributed replicated file systems, the method must block until the
     * replication quorum has been reached. If the calling thread is interrupted in the
     * process, it must fail with an {@code IOException} to indicate that persistence cannot
     * be guaranteed.
     *
     * <p>If this method throws an exception, the data in the stream cannot be assumed to be
     * persistent.
     *
     * <p>Implementation note: This overrides the method defined in {@link OutputStream}
     * as abstract to force implementations of the {@code FSDataOutputStream} to implement
     * this method directly.
     *
     * @throws IOException Thrown, if an error occurred while closing the stream or guaranteeing
     *                     that the data is persistent.
     */
    public abstract void close() throws IOException;
}

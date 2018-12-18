package lsieun.flink.core.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.fs.local.LocalFileSystem;

@Public
public abstract class FileSystem {
    // ------------------------------------------------------------------------
    //  File System Methods
    // ------------------------------------------------------------------------

    /**
     * Returns the path of the file system's current working directory.
     *
     * @return the path of the file system's current working directory
     */
    public abstract Path getWorkingDirectory();

    /**
     * Returns the path of the user's home directory in this file system.
     *
     * @return the path of the user's home directory in this file system.
     */
    public abstract Path getHomeDirectory();

    /**
     * Returns a URI whose scheme and authority identify this file system.
     *
     * @return a URI whose scheme and authority identify this file system
     */
    public abstract URI getUri();

    /**
     * Return a file status object that represents the path.
     *
     * @param f
     *        The path we want information from
     * @return a FileStatus object
     * @throws FileNotFoundException
     *         when the path does not exist;
     *         IOException see specific implementation
     */
    public abstract FileStatus getFileStatus(Path f) throws IOException;

    /**
     * Return an array containing hostnames, offset and size of
     * portions of the given file. For a nonexistent
     * file or regions, null will be returned.
     * This call is most helpful with DFS, where it returns
     * hostnames of machines that contain the given file.
     * The FileSystem will simply return an elt containing 'localhost'.
     */
    public abstract BlockLocation[] getFileBlockLocations(FileStatus file, long start, long len) throws IOException;

    /**
     * List the statuses of the files/directories in the given path if the path is
     * a directory.
     *
     * @param f
     *        given path
     * @return the statuses of the files/directories in the given path
     * @throws IOException
     */
    public abstract FileStatus[] listStatus(Path f) throws IOException;

    /**
     * Check if exists.
     *
     * @param f
     *        source file
     */
    public boolean exists(final Path f) throws IOException {
        try {
            return (getFileStatus(f) != null);
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * Delete a file.
     *
     * @param f
     *        the path to delete
     * @param recursive
     *        if path is a directory and set to <code>true</code>, the directory is deleted else throws an exception. In
     *        case of a file the recursive can be set to either <code>true</code> or <code>false</code>
     * @return <code>true</code> if delete is successful, <code>false</code> otherwise
     * @throws IOException
     */
    public abstract boolean delete(Path f, boolean recursive) throws IOException;

    /**
     * Make the given file and all non-existent parents into directories. Has the semantics of Unix 'mkdir -p'.
     * Existence of the directory hierarchy is not an error.
     *
     * @param f
     *        the directory/directories to be created
     * @return <code>true</code> if at least one new directory has been created, <code>false</code> otherwise
     * @throws IOException
     *         thrown if an I/O error occurs while creating the directory
     */
    public abstract boolean mkdirs(Path f) throws IOException;

    /**
     * Renames the file/directory src to dst.
     *
     * @param src
     *        the file/directory to rename
     * @param dst
     *        the new name of the file/directory
     * @return <code>true</code> if the renaming was successful, <code>false</code> otherwise
     * @throws IOException
     */
    public abstract boolean rename(Path src, Path dst) throws IOException;

    /**
     * Returns true if this is a distributed file system. A distributed file system here means
     * that the file system is shared among all Flink processes that participate in a cluster or
     * job and that all these processes can see the same files.
     *
     * @return True, if this is a distributed file system, false otherwise.
     */
    public abstract boolean isDistributedFS();

    /**
     * Gets a description of the characteristics of this file system.
     */
    public abstract FileSystemKind getKind();

    /**
     * Returns a reference to the {@link FileSystem} instance for accessing the
     * file system identified by the given {@link URI}.
     *
     * @param uri
     *        the {@link URI} identifying the file system
     * @return a reference to the {@link FileSystem} instance for accessing the file system identified by the given
     *         {@link URI}.
     * @throws IOException
     *         thrown if a reference to the file system instance could not be obtained
     */
    public static FileSystem get(URI uri) throws IOException {
        return new LocalFileSystem();
    }

    /**
     * Opens an FSDataInputStream at the indicated Path.
     *
     * @param f
     *        the file name to open
     * @param bufferSize
     *        the size of the buffer to be used.
     */
    public abstract FSDataInputStream open(Path f, int bufferSize) throws IOException;

    /**
     * Opens an FSDataInputStream at the indicated Path.
     *
     * @param f
     *        the file to open
     */
    public abstract FSDataInputStream open(Path f) throws IOException;
}

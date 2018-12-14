package lsieun.flink.core.fs;

import lsieun.flink.annotation.Public;

/**
 * Interface that represents the client side information for a file
 * independent of the file system.
 */
@Public
public interface FileStatus {

    /**
     * Return the length of this file.
     *
     * @return the length of this file
     */
    long getLen();

    /**
     *Get the block size of the file.
     *
     * @return the number of bytes
     */
    long getBlockSize();

    /**
     * Get the replication factor of a file.
     *
     * @return the replication factor of a file.
     */
    short getReplication();

    /**
     * Get the modification time of the file.
     *
     * @return the modification time of file in milliseconds since January 1, 1970 UTC.
     */
    long getModificationTime();

    /**
     * Get the access time of the file.
     *
     * @return the access time of file in milliseconds since January 1, 1970 UTC.
     */
    long getAccessTime();

    /**
     * Checks if this object represents a directory.
     *
     * @return <code>true</code> if this is a directory, <code>false</code> otherwise
     */
    boolean isDir();

    /**
     * Returns the corresponding Path to the FileStatus.
     *
     * @return the corresponding Path to the FileStatus
     */
    Path getPath();
}

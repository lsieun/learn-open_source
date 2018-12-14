package lsieun.flink.core.fs.local;

import java.io.File;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.fs.FileStatus;
import lsieun.flink.core.fs.FileSystem;
import lsieun.flink.core.fs.Path;

/**
 * The class <code>LocalFileStatus</code> provides an implementation of the {@link FileStatus} interface
 * for the local file system.
 */
@Internal
public class LocalFileStatus implements FileStatus {

    /**
     * The file this file status belongs to.
     */
    private final File file;

    /**
     * The path of this file this file status belongs to.
     */
    private final Path path;

    /**
     * Creates a <code>LocalFileStatus</code> object from a given {@link File} object.
     *
     * @param f
     *        the {@link File} object this <code>LocalFileStatus</code> refers to
     * @param fs
     *        the file system the corresponding file has been read from
     */
    public LocalFileStatus(final File f, final FileSystem fs) {
        this.file = f;
        this.path = new Path(fs.getUri().getScheme() + ":" + f.toURI().getPath());
    }

    @Override
    public long getAccessTime() {
        return 0; // We don't have access files for local files
    }

    @Override
    public long getBlockSize() {
        return this.file.length();
    }

    @Override
    public long getLen() {
        return this.file.length();
    }

    @Override
    public long getModificationTime() {
        return this.file.lastModified();
    }

    @Override
    public short getReplication() {
        return 1; // For local files replication is always 1
    }

    @Override
    public boolean isDir() {
        return this.file.isDirectory();
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    public File getFile() {
        return this.file;
    }

    @Override
    public String toString() {
        return "LocalFileStatus{" +
                "file=" + file +
                ", path=" + path +
                '}';
    }
}

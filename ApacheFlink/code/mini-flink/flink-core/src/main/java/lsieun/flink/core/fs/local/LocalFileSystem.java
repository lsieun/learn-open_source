package lsieun.flink.core.fs.local;

import static lsieun.flink.util.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.fs.FileStatus;
import lsieun.flink.core.fs.FileSystem;
import lsieun.flink.core.fs.FileSystemKind;
import lsieun.flink.core.fs.Path;
import lsieun.flink.util.OperatingSystem;

@Internal
public class LocalFileSystem extends FileSystem {
    /** The URI representing the local file system. */
    private static final URI LOCAL_URI = OperatingSystem.isWindows() ? URI.create("file:/") : URI.create("file:///");

    /** The shared instance of the local file system. */
    private static final LocalFileSystem INSTANCE = new LocalFileSystem();

    /** Path pointing to the current working directory.
     * Because Paths are not immutable, we cannot cache the proper path here */
    private final URI workingDir;

    /** Path pointing to the current user home directory.
     * Because Paths are not immutable, we cannot cache the proper path here. */
    private final URI homeDir;

    /** The host name of this machine. */
    private final String hostName;

    /**
     * Constructs a new <code>LocalFileSystem</code> object.
     */
    public LocalFileSystem() {
        this.workingDir = new File(System.getProperty("user.dir")).toURI();
        this.homeDir = new File(System.getProperty("user.home")).toURI();

        String tmp = "unknownHost";
        try {
            tmp = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.err.println("Could not resolve local host: " + e);
        }
        this.hostName = tmp;
    }

    @Override
    public URI getUri() {
        return LOCAL_URI;
    }

    @Override
    public Path getWorkingDirectory() {
        return new Path(workingDir);
    }

    @Override
    public Path getHomeDirectory() {
        return new Path(homeDir);
    }

    @Override
    public FileStatus getFileStatus(Path f) throws IOException {
        final File path = pathToFile(f);
        if (path.exists()) {
            return new LocalFileStatus(path, this);
        }
        else {
            throw new FileNotFoundException("File " + f + " does not exist or the user running "
                    + "Flink ('" + System.getProperty("user.name") + "') has insufficient permissions to access it.");
        }
    }

    @Override
    public FileStatus[] listStatus(final Path f) throws IOException {

        final File localf = pathToFile(f);
        FileStatus[] results;

        if (!localf.exists()) {
            return null;
        }
        if (localf.isFile()) {
            return new FileStatus[] { new LocalFileStatus(localf, this) };
        }

        final String[] names = localf.list();
        if (names == null) {
            return null;
        }
        results = new FileStatus[names.length];
        for (int i = 0; i < names.length; i++) {
            results[i] = getFileStatus(new Path(f, names[i]));
        }

        return results;
    }

    @Override
    public boolean exists(Path f) throws IOException {
        final File path = pathToFile(f);
        return path.exists();
    }

    @Override
    public boolean delete(final Path f, final boolean recursive) throws IOException {

        final File file = pathToFile(f);
        if (file.isFile()) {
            return file.delete();
        } else if ((!recursive) && file.isDirectory()) {
            File[] containedFiles = file.listFiles();
            if (containedFiles == null) {
                throw new IOException("Directory " + file.toString() + " does not exist or an I/O error occurred");
            } else if (containedFiles.length != 0) {
                throw new IOException("Directory " + file.toString() + " is not empty");
            }
        }

        return delete(file);
    }

    /**
     * Deletes the given file or directory.
     *
     * @param f
     *        the file to be deleted
     * @return <code>true</code> if all files were deleted successfully, <code>false</code> otherwise
     * @throws IOException
     *         thrown if an error occurred while deleting the files/directories
     */
    private boolean delete(final File f) throws IOException {

        if (f.isDirectory()) {
            final File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    final boolean del = delete(file);
                    if (!del) {
                        return false;
                    }
                }
            }
        } else {
            return f.delete();
        }

        // Now directory is empty
        return f.delete();
    }

    /**
     * Recursively creates the directory specified by the provided path.
     *
     * @return <code>true</code>if the directories either already existed or have been created successfully,
     *         <code>false</code> otherwise
     * @throws IOException
     *         thrown if an error occurred while creating the directory/directories
     */
    @Override
    public boolean mkdirs(final Path f) throws IOException {
        checkNotNull(f, "path is null");
        return mkdirsInternal(pathToFile(f));
    }

    private boolean mkdirsInternal(File file) throws IOException {
        if (file.isDirectory()) {
            return true;
        }
        else if (file.exists() && !file.isDirectory()) {
            // Important: The 'exists()' check above must come before the 'isDirectory()' check to
            //            be safe when multiple parallel instances try to create the directory

            // exists and is not a directory -> is a regular file
            throw new FileAlreadyExistsException(file.getAbsolutePath());
        }
        else {
            File parent = file.getParentFile();
            return (parent == null || mkdirsInternal(parent)) && (file.mkdir() || file.isDirectory());
        }
    }

    @Override
    public boolean rename(final Path src, final Path dst) throws IOException {
        final File srcFile = pathToFile(src);
        final File dstFile = pathToFile(dst);

        final File dstParent = dstFile.getParentFile();

        // Files.move fails if the destination directory doesn't exist
        //noinspection ResultOfMethodCallIgnored -- we don't care if the directory existed or was created
        dstParent.mkdirs();

        try {
            Files.move(srcFile.toPath(), dstFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        catch (NoSuchFileException | AccessDeniedException | DirectoryNotEmptyException | SecurityException ex) {
            // catch the errors that are regular "move failed" exceptions and return false
            return false;
        }
    }

    @Override
    public boolean isDistributedFS() {
        return false;
    }

    @Override
    public FileSystemKind getKind() {
        return FileSystemKind.FILE_SYSTEM;
    }

    // ------------------------------------------------------------------------

    /**
     * Converts the given Path to a File for this file system.
     *
     * <p>If the path is not absolute, it is interpreted relative to this FileSystem's working directory.
     */
    public File pathToFile(Path path) {
        if (!path.isAbsolute()) {
            path = new Path(getWorkingDirectory(), path);
        }
        return new File(path.toUri().getPath());
    }

    // ------------------------------------------------------------------------

    /**
     * Gets the URI that represents the local file system.
     * That URI is {@code "file:/"} on Windows platforms and {@code "file:///"} on other
     * UNIX family platforms.
     *
     * @return The URI that represents the local file system.
     */
    public static URI getLocalFsURI() {
        return LOCAL_URI;
    }

    /**
     * Gets the shared instance of this file system.
     *
     * @return The shared instance of this file system.
     */
    public static LocalFileSystem getSharedInstance() {
        return INSTANCE;
    }
}

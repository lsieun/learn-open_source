package lsieun.flink.core.fs;

import lsieun.flink.annotation.PublicEvolving;

/**
 * An enumeration defining the kind and characteristics of a {@link FileSystem}.
 */
@PublicEvolving
public enum FileSystemKind {

    /**
     * An actual file system, with files and directories.
     */
    FILE_SYSTEM,

    /**
     * An Object store. Files correspond to objects.
     * There are not really directories, but a directory-like structure may be mimicked
     * by hierarchical naming of files.
     */
    OBJECT_STORE
}

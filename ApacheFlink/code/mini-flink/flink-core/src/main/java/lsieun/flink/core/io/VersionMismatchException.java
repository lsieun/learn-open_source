package lsieun.flink.core.io;


import java.io.IOException;

import lsieun.flink.annotation.PublicEvolving;

/**
 * This exception signals that incompatible versions have been found during serialization.
 */
@PublicEvolving
public class VersionMismatchException extends IOException {

    private static final long serialVersionUID = 7024258967585372438L;

    public VersionMismatchException() {
    }

    public VersionMismatchException(String message) {
        super(message);
    }

    public VersionMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public VersionMismatchException(Throwable cause) {
        super(cause);
    }
}

package lsieun.flink.api.common.functions;


import lsieun.flink.annotation.Public;
import lsieun.flink.api.common.InvalidProgramException;

/**
 * A special case of the {@link InvalidProgramException}, indicating that the types used in
 * an operation are invalid or inconsistent.
 */
@Public
public class InvalidTypesException extends InvalidProgramException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new exception with no message.
     */
    public InvalidTypesException() {
        super();
    }

    /**
     * Creates a new exception with the given message.
     *
     * @param message The exception message.
     */
    public InvalidTypesException(String message) {
        super(message);
    }

    public InvalidTypesException(String message, Throwable e) {
        super(message, e);
    }
}

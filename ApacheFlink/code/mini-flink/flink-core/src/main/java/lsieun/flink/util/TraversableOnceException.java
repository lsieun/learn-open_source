package lsieun.flink.util;


import lsieun.flink.annotation.Public;

/**
 * An exception, indicating that an {@link java.lang.Iterable} can only be traversed once, but has been attempted
 * to traverse an additional time.
 */
@Public
public class TraversableOnceException extends RuntimeException {

    private static final long serialVersionUID = 7636881584773577290L;

    /**
     * Creates a new exception with a default message.
     */
    public TraversableOnceException() {
        super("The Iterable can be iterated over only once. Only the first call to 'iterator()' will succeed.");
    }
}

package lsieun.flink.configuration;

import lsieun.flink.annotation.PublicEvolving;

/**
 * An {@code IllegalConfigurationException} is thrown when
 * the values in a given {@link Configuration} are not valid. This may refer
 * to the Flink configuration with which the framework is started,
 * or a Configuration passed internally between components.
 */
@PublicEvolving
public class IllegalConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 695506964810499989L;

    /**
     * Constructs an new IllegalConfigurationException with the given error message.
     *
     * @param message The error message for the exception.
     */
    public IllegalConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs an new IllegalConfigurationException with the given error message
     * format and arguments.
     *
     * @param format The error message format for the exception.
     * @param arguments The arguments for the format.
     */
    public IllegalConfigurationException(String format, Object... arguments) {
        super(String.format(format, arguments));
    }

    /**
     * Constructs an new IllegalConfigurationException with the given error message
     * and a given cause.
     *
     * @param message The error message for the exception.
     * @param cause The exception that caused this exception.
     */
    public IllegalConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

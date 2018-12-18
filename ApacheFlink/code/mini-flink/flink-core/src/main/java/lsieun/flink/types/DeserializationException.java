package lsieun.flink.types;


import lsieun.flink.annotation.Public;

/**
 * An exception specifying that the deserialization caused an error.
 */
@Public
public class DeserializationException extends RuntimeException
{
	/**
	 * UID for serialization interoperability. 
	 */
	private static final long serialVersionUID = -8725950711347033148L;

	/**
	 * Constructs an {@code DeserializationException} with {@code null}
	 * as its error detail message.
	 */
	public DeserializationException() {
		super();
	}

	/**
	 * Constructs an {@code DeserializationException} with the specified detail message
	 * and cause.
	 * 
	 * @param message The detail message.
	 * @param cause The cause of the exception.
	 */
	public DeserializationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an {@code DeserializationException} with the specified detail message.
	 *
	 * @param message The detail message.
	 */
	public DeserializationException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code DeserializationException} with the specified cause.
	 * 
	 * @param cause The cause of the exception.
	 */
	public DeserializationException(Throwable cause) {
		super(cause);
	}
}

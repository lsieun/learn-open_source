package lsieun.flink.types.parser;

import java.math.BigDecimal;

import lsieun.flink.annotation.PublicEvolving;

/**
 * Parses a text field into a {@link java.math.BigDecimal}.
 */
@PublicEvolving
public class BigDecParser extends FieldParser<BigDecimal> {

	// -------------------------------------- Constants -------------------------------------------

	private static final BigDecimal BIG_DECIMAL_INSTANCE = BigDecimal.ZERO;

	// -------------------------------------- Static Methods -------------------------------------------

	/**
	 * Static utility to parse a field of type BigDecimal from a byte sequence that represents text
	 * characters
	 * (such as when read from a file stream).
	 *
	 * @param bytes    The bytes containing the text data that should be parsed.
	 * @param startPos The offset to start the parsing.
	 * @param length   The length of the byte sequence (counting from the offset).
	 * @return The parsed value.
	 * @throws IllegalArgumentException Thrown when the value cannot be parsed because the text
	 * represents not a correct number.
	 */
	public static final BigDecimal parseField(byte[] bytes, int startPos, int length) {
		return parseField(bytes, startPos, length, (char) 0xffff);
	}

	/**
	 * Static utility to parse a field of type BigDecimal from a byte sequence that represents text
	 * characters
	 * (such as when read from a file stream).
	 *
	 * @param bytes     The bytes containing the text data that should be parsed.
	 * @param startPos  The offset to start the parsing.
	 * @param length    The length of the byte sequence (counting from the offset).
	 * @param delimiter The delimiter that terminates the field.
	 * @return The parsed value.
	 * @throws IllegalArgumentException Thrown when the value cannot be parsed because the text
	 * represents not a correct number.
	 */
	public static final BigDecimal parseField(byte[] bytes, int startPos, int length, char delimiter) {
		if (length <= 0) {
			throw new NumberFormatException("Invalid input: Empty string");
		}
		int i = 0;
		final byte delByte = (byte) delimiter;

		while (i < length && bytes[startPos + i] != delByte) {
			i++;
		}

		if (i > 0 &&
				(Character.isWhitespace(bytes[startPos]) || Character.isWhitespace(bytes[startPos + i - 1]))) {
			throw new NumberFormatException("There is leading or trailing whitespace in the numeric field.");
		}

		final char[] chars = new char[i];
		for (int j = 0; j < i; j++) {
			final byte b = bytes[startPos + j];
			if ((b < '0' || b > '9') && b != '-' && b != '+' && b != '.' && b != 'E' && b != 'e') {
				throw new NumberFormatException();
			}
			chars[j] = (char) bytes[startPos + j];
		}
		return new BigDecimal(chars);
	}

	// -------------------------------------- Fields -------------------------------------------

	private BigDecimal result;
	private char[] reuse = null;

	// -------------------------------------- Methods -------------------------------------------

	@Override
	public int parseField(byte[] bytes, int startPos, int limit, byte[] delimiter, BigDecimal reusable) {
		final int endPos = nextStringEndPos(bytes, startPos, limit, delimiter);
		if (endPos < 0) {
			return -1;
		}

		try {
			final int length = endPos - startPos;
			if (reuse == null || reuse.length < length) {
				reuse = new char[length];
			}
			for (int j = 0; j < length; j++) {
				final byte b = bytes[startPos + j];
				if ((b < '0' || b > '9') && b != '-' && b != '+' && b != '.' && b != 'E' && b != 'e') {
					setErrorState(ParseErrorState.NUMERIC_VALUE_ILLEGAL_CHARACTER);
					return -1;
				}
				reuse[j] = (char) bytes[startPos + j];
			}

			this.result = new BigDecimal(reuse, 0, length);
			return (endPos == limit) ? limit : endPos + delimiter.length;
		} catch (NumberFormatException e) {
			setErrorState(ParseErrorState.NUMERIC_VALUE_FORMAT_ERROR);
			return -1;
		}
	}

	@Override
	public BigDecimal createValue() {
		return BIG_DECIMAL_INSTANCE;
	}

	@Override
	public BigDecimal getLastResult() {
		return this.result;
	}

}

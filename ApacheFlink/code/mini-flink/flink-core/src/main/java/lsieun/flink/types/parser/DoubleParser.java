package lsieun.flink.types.parser;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.configuration.ConfigConstants;

/**
 * Parses a text field into a Double.
 */
@PublicEvolving
public class DoubleParser extends FieldParser<Double> {

	private static final Double DOUBLE_INSTANCE = Double.valueOf(0.0);

	private double result;

	@Override
	public int parseField(byte[] bytes, int startPos, int limit, byte[] delimiter, Double reusable) {
		final int endPos = nextStringEndPos(bytes, startPos, limit, delimiter);
		if (endPos < 0) {
			return -1;
		}

		if (endPos > startPos &&
				(Character.isWhitespace(bytes[startPos]) || Character.isWhitespace(bytes[(endPos - 1)]))) {
			setErrorState(ParseErrorState.NUMERIC_VALUE_ILLEGAL_CHARACTER);
			return -1;
		}

		String str = new String(bytes, startPos, endPos - startPos, ConfigConstants.DEFAULT_CHARSET);
		try {
			this.result = Double.parseDouble(str);
			return (endPos == limit) ? limit : endPos + delimiter.length;
		} catch (NumberFormatException e) {
			setErrorState(ParseErrorState.NUMERIC_VALUE_FORMAT_ERROR);
			return -1;
		}
	}

	@Override
	public Double createValue() {
		return DOUBLE_INSTANCE;
	}

	@Override
	public Double getLastResult() {
		return Double.valueOf(this.result);
	}

	/**
	 * Static utility to parse a field of type double from a byte sequence that represents text 
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
	public static final double parseField(byte[] bytes, int startPos, int length) {
		return parseField(bytes, startPos, length, (char) 0xffff);
	}

	/**
	 * Static utility to parse a field of type double from a byte sequence that represents text 
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
	public static final double parseField(byte[] bytes, int startPos, int length, char delimiter) {
		final int limitedLen = nextStringLength(bytes, startPos, length, delimiter);

		if (limitedLen > 0 &&
				(Character.isWhitespace(bytes[startPos]) || Character.isWhitespace(bytes[startPos + limitedLen - 1]))) {
			throw new NumberFormatException("There is leading or trailing whitespace in the numeric field.");
		}

		final String str = new String(bytes, startPos, limitedLen, ConfigConstants.DEFAULT_CHARSET);
		return Double.parseDouble(str);
	}
}

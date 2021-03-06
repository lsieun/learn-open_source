package lsieun.flink.types.parser;

import java.sql.Date;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.configuration.ConfigConstants;

/**
 * Parses a text field into a {@link java.sql.Date}.
 */
@PublicEvolving
public class SqlDateParser extends FieldParser<Date> {

	private static final Date DATE_INSTANCE = new Date(0L);

	private Date result;

	@Override
	public int parseField(byte[] bytes, int startPos, int limit, byte[] delimiter, Date reusable) {
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
			this.result = Date.valueOf(str);
			return (endPos == limit) ? limit : endPos + delimiter.length;
		} catch (IllegalArgumentException e) {
			setErrorState(ParseErrorState.NUMERIC_VALUE_FORMAT_ERROR);
			return -1;
		}
	}

	@Override
	public Date createValue() {
		return DATE_INSTANCE;
	}

	@Override
	public Date getLastResult() {
		return this.result;
	}

	/**
	 * Static utility to parse a field of type Date from a byte sequence that represents text
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
	public static final Date parseField(byte[] bytes, int startPos, int length) {
		return parseField(bytes, startPos, length, (char) 0xffff);
	}

	/**
	 * Static utility to parse a field of type Date from a byte sequence that represents text
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
	public static final Date parseField(byte[] bytes, int startPos, int length, char delimiter) {
		final int limitedLen = nextStringLength(bytes, startPos, length, delimiter);

		if (limitedLen > 0 &&
				(Character.isWhitespace(bytes[startPos]) || Character.isWhitespace(bytes[startPos + limitedLen - 1]))) {
			throw new NumberFormatException("There is leading or trailing whitespace in the numeric field.");
		}

		final String str = new String(bytes, startPos, limitedLen, ConfigConstants.DEFAULT_CHARSET);
		return Date.valueOf(str);
	}
}

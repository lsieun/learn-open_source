package lsieun.flink.types.parser;

import java.sql.Time;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.configuration.ConfigConstants;

/**
 * Parses a text field into a {@link Time}.
 */
@PublicEvolving
public class SqlTimeParser extends FieldParser<Time> {

	private static final Time TIME_INSTANCE = new Time(0L);

	private Time result;

	@Override
	public int parseField(byte[] bytes, int startPos, int limit, byte[] delimiter, Time reusable) {
		final int endPos = nextStringEndPos(bytes, startPos, limit, delimiter);
		if (endPos < 0) {
			return -1;
		}

		String str = new String(bytes, startPos, endPos - startPos, ConfigConstants.DEFAULT_CHARSET);
		try {
			this.result = Time.valueOf(str);
			return (endPos == limit) ? limit : endPos + delimiter.length;
		} catch (IllegalArgumentException e) {
			setErrorState(ParseErrorState.NUMERIC_VALUE_FORMAT_ERROR);
			return -1;
		}
	}

	@Override
	public Time createValue() {
		return TIME_INSTANCE;
	}

	@Override
	public Time getLastResult() {
		return this.result;
	}

	/**
	 * Static utility to parse a field of type Time from a byte sequence that represents text
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
	public static final Time parseField(byte[] bytes, int startPos, int length) {
		return parseField(bytes, startPos, length, (char) 0xffff);
	}

	/**
	 * Static utility to parse a field of type Time from a byte sequence that represents text
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
	public static final Time parseField(byte[] bytes, int startPos, int length, char delimiter) {
		final int limitedLen = nextStringLength(bytes, startPos, length, delimiter);

		if (limitedLen > 0 &&
				(Character.isWhitespace(bytes[startPos]) || Character.isWhitespace(bytes[startPos + limitedLen - 1]))) {
			throw new NumberFormatException("There is leading or trailing whitespace in the numeric field.");
		}

		final String str = new String(bytes, startPos, limitedLen, ConfigConstants.DEFAULT_CHARSET);
		return Time.valueOf(str);
	}
}

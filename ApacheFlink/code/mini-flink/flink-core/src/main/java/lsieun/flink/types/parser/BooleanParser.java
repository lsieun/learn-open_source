package lsieun.flink.types.parser;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.configuration.ConfigConstants;

@PublicEvolving
public class BooleanParser extends FieldParser<Boolean> {

	// -------------------------------------- Constants -------------------------------------------

	/** Values for true and false respectively. Must be lower case. */
	private static final byte[][] TRUE = new byte[][] {
			"true".getBytes(ConfigConstants.DEFAULT_CHARSET),
			"1".getBytes(ConfigConstants.DEFAULT_CHARSET)
	};
	private static final byte[][] FALSE = new byte[][] {
			"false".getBytes(ConfigConstants.DEFAULT_CHARSET),
			"0".getBytes(ConfigConstants.DEFAULT_CHARSET)
	};

	// -------------------------------------- Static Methods -------------------------------------------

	/**
	 * Checks if a part of a byte array matches another byte array with chars (case-insensitive).
	 * @param source The source byte array.
	 * @param start The offset into the source byte array.
	 * @param length The length of the match.
	 * @param other The byte array which is fully compared to the part of the source array.
	 * @return true if other can be found in the specified part of source, false otherwise.
	 */
	private static boolean byteArrayEquals(byte[] source, int start, int length, byte[] other) {
		if (length != other.length) {
			return false;
		}
		for (int i = 0; i < other.length; i++) {
			if (Character.toLowerCase(source[i + start]) != other[i]) {
				return false;
			}
		}
		return true;
	}

	// -------------------------------------- Fields -------------------------------------------

	private boolean result;

	// -------------------------------------- Methods -------------------------------------------

	@Override
	public int parseField(byte[] bytes, int startPos, int limit, byte[] delimiter, Boolean reuse) {

		final int i = nextStringEndPos(bytes, startPos, limit, delimiter);

		if (i < 0) {
			return -1;
		}

		for (byte[] aTRUE : TRUE) {
			if (byteArrayEquals(bytes, startPos, i - startPos, aTRUE)) {
				result = true;
				return (i == limit) ? limit : i + delimiter.length;
			}
		}

		for (byte[] aFALSE : FALSE) {
			if (byteArrayEquals(bytes, startPos, i - startPos, aFALSE)) {
				result = false;
				return (i == limit) ? limit : i + delimiter.length;
			}
		}

		setErrorState(ParseErrorState.BOOLEAN_INVALID);
		return -1;
	}

	@Override
	public Boolean getLastResult() {
		return result;
	}

	@Override
	public Boolean createValue() {
		return false;
	}
}

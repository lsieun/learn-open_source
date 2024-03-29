package lsieun.flink.types.parser;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.types.LongValue;

/**
 * Parses a decimal text field into a LongValue.
 * Only characters '1' to '0' and '-' are allowed.
 */
@PublicEvolving
public class LongValueParser extends FieldParser<LongValue> {
	
	private LongValue result;
	
	@Override
	public int parseField(byte[] bytes, int startPos, int limit, byte[] delimiter, LongValue reusable) {

		if (startPos == limit) {
			setErrorState(ParseErrorState.EMPTY_COLUMN);
			return -1;
		}

		long val = 0;
		boolean neg = false;

		final int delimLimit = limit - delimiter.length + 1;

		this.result = reusable;
		
		if (bytes[startPos] == '-') {
			neg = true;
			startPos++;
			
			// check for empty field with only the sign
			if (startPos == limit || (startPos < delimLimit && delimiterNext(bytes, startPos, delimiter))) {
				setErrorState(ParseErrorState.NUMERIC_VALUE_ORPHAN_SIGN);
				return -1;
			}
		}
		
		for (int i = startPos; i < limit; i++) {
			if (i < delimLimit && delimiterNext(bytes, i, delimiter)) {
				if (i == startPos) {
					setErrorState(ParseErrorState.EMPTY_COLUMN);
					return -1;
				}
				reusable.setValue(neg ? -val : val);
				return i + delimiter.length;
			}
			if (bytes[i] < 48 || bytes[i] > 57) {
				setErrorState(ParseErrorState.NUMERIC_VALUE_ILLEGAL_CHARACTER);
				return -1;
			}
			val *= 10;
			val += bytes[i] - 48;

			// check for overflow / underflow
			if (val < 0) {
				// this is an overflow/underflow, unless we hit exactly the Long.MIN_VALUE
				if (neg && val == Long.MIN_VALUE) {
					reusable.setValue(Long.MIN_VALUE);
					
					if (i+1 >= limit) {
						return limit;
					} else if (i + 1 < delimLimit && delimiterNext(bytes, i + 1, delimiter)) {
						return i + 1 + delimiter.length;
					} else {
						setErrorState(ParseErrorState.NUMERIC_VALUE_OVERFLOW_UNDERFLOW);
						return -1;
					}
				}
				else {
					setErrorState(ParseErrorState.NUMERIC_VALUE_OVERFLOW_UNDERFLOW);
					return -1;
				}
			}
		}

		reusable.setValue(neg ? -val : val);
		return limit;
	}
	
	@Override
	public LongValue createValue() {
		return new LongValue();
	}
	
	@Override
	public LongValue getLastResult() {
		return this.result;
	}
}

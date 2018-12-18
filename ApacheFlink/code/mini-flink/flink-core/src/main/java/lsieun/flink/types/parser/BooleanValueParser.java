package lsieun.flink.types.parser;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.types.BooleanValue;

@PublicEvolving
public class BooleanValueParser extends FieldParser<BooleanValue> {

	private BooleanParser parser = new BooleanParser();

	private BooleanValue result;

	@Override
	public int parseField(byte[] bytes, int startPos, int limit, byte[] delim, BooleanValue reuse) {
		int returnValue = parser.parseField(bytes, startPos, limit, delim, reuse.getValue());
		setErrorState(parser.getErrorState());
		reuse.setValue(parser.getLastResult());
		result = reuse;
		return returnValue;
	}

	@Override
	public BooleanValue getLastResult() {
		return result;
	}

	@Override
	public BooleanValue createValue() {
		return new BooleanValue(false);
	}
}

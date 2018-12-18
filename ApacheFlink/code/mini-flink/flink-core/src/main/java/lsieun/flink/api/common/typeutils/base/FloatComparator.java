package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.MemorySegment;

@Internal
public final class FloatComparator extends BasicTypeComparator<Float> {

	private static final long serialVersionUID = 1L;

	
	public FloatComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public int compareSerialized(DataInputView firstSource, DataInputView secondSource) throws IOException {
		float l1 = firstSource.readFloat();
		float l2 = secondSource.readFloat();
		int comp = (l1 < l2 ? -1 : (l1 > l2 ? 1 : 0)); 
		return ascendingComparison ? comp : -comp; 
	}


	@Override
	public boolean supportsNormalizedKey() {
		return false;
	}

	@Override
	public int getNormalizeKeyLen() {
		return 0;
	}

	@Override
	public boolean isNormalizedKeyPrefixOnly(int keyBytes) {
		return true;
	}

	@Override
	public void putNormalizedKey(Float value, MemorySegment target, int offset, int numBytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FloatComparator duplicate() {
		return new FloatComparator(ascendingComparison);
	}
}

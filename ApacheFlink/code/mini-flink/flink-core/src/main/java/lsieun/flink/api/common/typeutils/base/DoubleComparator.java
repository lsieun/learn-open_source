package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.MemorySegment;

@Internal
public final class DoubleComparator extends BasicTypeComparator<Double> {

	private static final long serialVersionUID = 1L;

	
	public DoubleComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public int compareSerialized(DataInputView firstSource, DataInputView secondSource) throws IOException {
		double l1 = firstSource.readDouble(); 
		double l2 = secondSource.readDouble(); 
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
	public void putNormalizedKey(Double value, MemorySegment target, int offset, int numBytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DoubleComparator duplicate() {
		return new DoubleComparator(ascendingComparison);
	}
}

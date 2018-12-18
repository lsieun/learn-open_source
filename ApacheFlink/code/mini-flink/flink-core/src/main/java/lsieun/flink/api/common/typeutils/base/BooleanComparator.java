package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.MemorySegment;

@Internal
public final class BooleanComparator extends BasicTypeComparator<Boolean> {

	private static final long serialVersionUID = 1L;

	
	public BooleanComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public int compareSerialized(DataInputView firstSource, DataInputView secondSource) throws IOException {
		final int fs = firstSource.readBoolean() ? 1 : 0;
		final int ss = secondSource.readBoolean() ? 1 : 0;
		int comp = fs - ss; 
		return ascendingComparison ? comp : -comp; 
	}


	@Override
	public boolean supportsNormalizedKey() {
		return true;
	}

	@Override
	public int getNormalizeKeyLen() {
		return 1;
	}

	@Override
	public boolean isNormalizedKeyPrefixOnly(int keyBytes) {
		return keyBytes < 1;
	}

	@Override
	public void putNormalizedKey(Boolean value, MemorySegment target, int offset, int numBytes) {
		if (numBytes > 0) {
			target.put(offset, (byte) (value.booleanValue() ? 1 : 0));
			
			for (offset = offset + 1; numBytes > 1; numBytes--) {
				target.put(offset++, (byte) 0);
			}
		}
	}

	@Override
	public BooleanComparator duplicate() {
		return new BooleanComparator(ascendingComparison);
	}
}

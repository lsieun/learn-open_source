package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.MemorySegment;

@Internal
public final class IntComparator extends BasicTypeComparator<Integer> {

	private static final long serialVersionUID = 1L;

	
	public IntComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public int compareSerialized(DataInputView firstSource, DataInputView secondSource) throws IOException {
		int i1 = firstSource.readInt();
		int i2 = secondSource.readInt();
		int comp = (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1)); 
		return ascendingComparison ? comp : -comp; 
	}


	@Override
	public boolean supportsNormalizedKey() {
		return true;
	}

	@Override
	public int getNormalizeKeyLen() {
		return 4;
	}

	@Override
	public boolean isNormalizedKeyPrefixOnly(int keyBytes) {
		return keyBytes < 4;
	}

	@Override
	public void putNormalizedKey(Integer iValue, MemorySegment target, int offset, int numBytes) {
		int value = iValue.intValue() - Integer.MIN_VALUE;
		
		// see IntValue for an explanation of the logic
		if (numBytes == 4) {
			// default case, full normalized key
			target.putIntBigEndian(offset, value);
		}
		else if (numBytes <= 0) {
		}
		else if (numBytes < 4) {
			for (int i = 0; numBytes > 0; numBytes--, i++) {
				target.put(offset + i, (byte) (value >>> ((3-i)<<3)));
			}
		}
		else {
			target.putLongBigEndian(offset, value);
			for (int i = 4; i < numBytes; i++) {
				target.put(offset + i, (byte) 0);
			}
		}
	}

	@Override
	public IntComparator duplicate() {
		return new IntComparator(ascendingComparison);
	}
}

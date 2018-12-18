package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.MemorySegment;

@Internal
public final class ByteComparator extends BasicTypeComparator<Byte> {

	private static final long serialVersionUID = 1L;

	
	public ByteComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public int compareSerialized(DataInputView firstSource, DataInputView secondSource) throws IOException {
		byte b1 = firstSource.readByte();
		byte b2 = secondSource.readByte();
		int comp = (b1 < b2 ? -1 : (b1 == b2 ? 0 : 1)); 
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
	public void putNormalizedKey(Byte value, MemorySegment target, int offset, int numBytes) {
		if (numBytes == 1) {
			// default case, full normalized key. need to explicitly convert to int to
			// avoid false results due to implicit type conversion to int when subtracting
			// the min byte value
			int highByte = value & 0xff;
			highByte -= Byte.MIN_VALUE;
			target.put(offset, (byte) highByte);
		}
		else if (numBytes <= 0) {
		}
		else {
			int highByte = value & 0xff;
			highByte -= Byte.MIN_VALUE;
			target.put(offset, (byte) highByte);
			for (int i = 1; i < numBytes; i++) {
				target.put(offset + i, (byte) 0);
			}
		}
	}

	@Override
	public ByteComparator duplicate() {
		return new ByteComparator(ascendingComparison);
	}
}

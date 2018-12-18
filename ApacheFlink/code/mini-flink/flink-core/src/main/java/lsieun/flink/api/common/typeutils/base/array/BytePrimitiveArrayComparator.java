package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.api.common.typeutils.base.ByteComparator;

@Internal
public class BytePrimitiveArrayComparator extends PrimitiveArrayComparator<byte[], ByteComparator> {
	public BytePrimitiveArrayComparator(boolean ascending) {
		super(ascending, new ByteComparator(ascending));
	}

	@Override
	public int hash(byte[] record) {
		int result = 0;
		for (byte field : record) {
			result += (int) field;
		}
		return result;
	}

	@Override
	public int compare(byte[] first, byte[] second) {
		for (int x = 0; x < min(first.length, second.length); x++) {
			int cmp = first[x] - second[x];
			if (cmp != 0) {
				return ascending ? cmp : -cmp;
			}
		}
		int cmp = first.length - second.length;
		return ascending ? cmp : -cmp;
	}

	@Override
	public TypeComparator<byte[]> duplicate() {
		BytePrimitiveArrayComparator dupe = new BytePrimitiveArrayComparator(this.ascending);
		dupe.setReference(this.reference);
		return dupe;
	}
}

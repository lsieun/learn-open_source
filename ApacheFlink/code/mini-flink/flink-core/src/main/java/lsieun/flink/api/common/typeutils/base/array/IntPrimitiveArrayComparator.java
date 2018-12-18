package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.base.IntComparator;

@Internal
public class IntPrimitiveArrayComparator extends PrimitiveArrayComparator<int[], IntComparator> {
	public IntPrimitiveArrayComparator(boolean ascending) {
		super(ascending, new IntComparator(ascending));
	}

	@Override
	public int hash(int[] record) {
		int result = 0;
		for (int field : record) {
			result += field;
		}
		return result;
	}

	@Override
	public int compare(int[] first, int[] second) {
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
	public TypeComparator<int[]> duplicate() {
		IntPrimitiveArrayComparator dupe = new IntPrimitiveArrayComparator(this.ascending);
		dupe.setReference(this.reference);
		return dupe;
	}
}

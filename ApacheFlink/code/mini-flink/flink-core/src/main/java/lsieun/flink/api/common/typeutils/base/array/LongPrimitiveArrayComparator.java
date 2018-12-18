package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.api.common.typeutils.base.LongComparator;

@Internal
public class LongPrimitiveArrayComparator extends PrimitiveArrayComparator<long[], LongComparator> {
	public LongPrimitiveArrayComparator(boolean ascending) {
		super(ascending, new LongComparator(ascending));
	}

	@Override
	public int hash(long[] record) {
		int result = 0;
		for (long field : record) {
			result += (int) (field ^ (field >>> 32));
		}
		return result;
	}

	@Override
	public int compare(long[] first, long[] second) {
		for (int x = 0; x < min(first.length, second.length); x++) {
			int cmp = first[x] < second[x] ? -1 : (first[x] == second[x] ? 0 : 1);
			if (cmp != 0) {
				return ascending ? cmp : -cmp;
			}
		}
		int cmp = first.length - second.length;
		return ascending ? cmp : -cmp;
	}

	@Override
	public TypeComparator<long[]> duplicate() {
		LongPrimitiveArrayComparator dupe = new LongPrimitiveArrayComparator(this.ascending);
		dupe.setReference(this.reference);
		return dupe;
	}
}

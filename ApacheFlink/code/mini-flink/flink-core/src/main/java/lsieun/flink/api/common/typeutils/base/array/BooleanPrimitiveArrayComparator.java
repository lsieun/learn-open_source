package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.api.common.typeutils.base.BooleanComparator;


@Internal
public class BooleanPrimitiveArrayComparator extends PrimitiveArrayComparator<boolean[], BooleanComparator> {
	public BooleanPrimitiveArrayComparator(boolean ascending) {
		super(ascending, new BooleanComparator(ascending));
	}

	@Override
	public int hash(boolean[] record) {
		int result = 0;
		for (boolean field : record) {
			result += field ? 1231 : 1237;
		}
		return result;
	}

	@Override
	public int compare(boolean[] first, boolean[] second) {
		for (int x = 0; x < min(first.length, second.length); x++) {
			int cmp = (second[x] == first[x] ? 0 : (first[x] ? 1 : -1));
			if (cmp != 0) {
				return ascending ? cmp : -cmp;
			}
		}
		int cmp = first.length - second.length;
		return ascending ? cmp : -cmp;
	}

	@Override
	public TypeComparator<boolean[]> duplicate() {
		BooleanPrimitiveArrayComparator dupe = new BooleanPrimitiveArrayComparator(this.ascending);
		dupe.setReference(this.reference);
		return dupe;
	}
}

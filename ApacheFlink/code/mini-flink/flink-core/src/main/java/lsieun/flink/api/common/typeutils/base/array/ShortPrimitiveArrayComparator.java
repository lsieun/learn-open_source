package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.base.ShortComparator;

@Internal
public class ShortPrimitiveArrayComparator extends PrimitiveArrayComparator<short[], ShortComparator> {
	public ShortPrimitiveArrayComparator(boolean ascending) {
		super(ascending, new ShortComparator(ascending));
	}

	@Override
	public int hash(short[] record) {
		int result = 0;
		for (short field : record) {
			result += (int) field;
		}
		return result;
	}

	@Override
	public int compare(short[] first, short[] second) {
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
	public TypeComparator<short[]> duplicate() {
		ShortPrimitiveArrayComparator dupe = new ShortPrimitiveArrayComparator(this.ascending);
		dupe.setReference(this.reference);
		return dupe;
	}
}

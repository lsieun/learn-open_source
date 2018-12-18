package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.api.common.typeutils.base.CharComparator;

@Internal
public class CharPrimitiveArrayComparator extends PrimitiveArrayComparator<char[], CharComparator> {
	public CharPrimitiveArrayComparator(boolean ascending) {
		super(ascending, new CharComparator(ascending));
	}

	@Override
	public int hash(char[] record) {
		int result = 0;
		for (char field : record) {
			result += (int) field;
		}
		return result;
	}

	@Override
	public int compare(char[] first, char[] second) {
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
	public TypeComparator<char[]> duplicate() {
		CharPrimitiveArrayComparator dupe = new CharPrimitiveArrayComparator(this.ascending);
		dupe.setReference(this.reference);
		return dupe;
	}
}

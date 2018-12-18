package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.api.common.typeutils.base.FloatComparator;

@Internal
public class FloatPrimitiveArrayComparator extends PrimitiveArrayComparator<float[], FloatComparator> {
	public FloatPrimitiveArrayComparator(boolean ascending) {
		super(ascending, new FloatComparator(ascending));
	}

	@Override
	public int hash(float[] record) {
		int result = 0;
		for (float field : record) {
			result += Float.floatToIntBits(field);
		}
		return result;
	}

	@Override
	public int compare(float[] first, float[] second) {
		for (int x = 0; x < min(first.length, second.length); x++) {
			int cmp = Float.compare(first[x], second[x]);
			if (cmp != 0) {
				return ascending ? cmp : -cmp;
			}
		}
		int cmp = first.length - second.length;
		return ascending ? cmp : -cmp;
	}

	@Override
	public TypeComparator<float[]> duplicate() {
		FloatPrimitiveArrayComparator dupe = new FloatPrimitiveArrayComparator(this.ascending);
		dupe.setReference(this.reference);
		return dupe;
	}
}

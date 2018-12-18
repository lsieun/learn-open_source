package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.base.DoubleComparator;

@Internal
public class DoublePrimitiveArrayComparator extends PrimitiveArrayComparator<double[], DoubleComparator> {
	public DoublePrimitiveArrayComparator(boolean ascending) {
		super(ascending, new DoubleComparator(ascending));
	}

	@Override
	public int hash(double[] record) {
		int result = 0;
		for (double field : record) {
			long bits = Double.doubleToLongBits(field);
			result += (int) (bits ^ (bits >>> 32));
		}
		return result;
	}

	@Override
	public int compare(double[] first, double[] second) {
		for (int x = 0; x < min(first.length, second.length); x++) {
			int cmp = Double.compare(first[x], second[x]);
			if (cmp != 0) {
				return ascending ? cmp : -cmp;
			}
		}
		int cmp = first.length - second.length;
		return ascending ? cmp : -cmp;
	}

	@Override
	public TypeComparator<double[]> duplicate() {
		DoublePrimitiveArrayComparator dupe = new DoublePrimitiveArrayComparator(this.ascending);
		dupe.setReference(this.reference);
		return dupe;
	}
}

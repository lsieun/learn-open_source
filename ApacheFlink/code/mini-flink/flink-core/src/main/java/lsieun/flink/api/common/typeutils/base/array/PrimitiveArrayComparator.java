package lsieun.flink.api.common.typeutils.base.array;

import static java.lang.Math.min;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.api.common.typeutils.base.BasicTypeComparator;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.core.memory.MemorySegment;

@Internal
public abstract class PrimitiveArrayComparator<T, C extends BasicTypeComparator> extends TypeComparator<T> {
	// For use by getComparators
	@SuppressWarnings("rawtypes")
	private final TypeComparator[] comparators = new TypeComparator[]{this};

	protected final boolean ascending;
	protected transient T reference;
	protected final C comparator;

	public PrimitiveArrayComparator(boolean ascending, C comparator) {
		this.ascending = ascending;
		this.comparator = comparator;
	}

	@Override
	public void setReference(T toCompare) {
		this.reference = toCompare;
	}

	@Override
	public boolean equalToReference(T candidate) {
		return compare(this.reference, candidate) == 0;
	}

	@Override
	public int compareToReference(TypeComparator<T> referencedComparator) {
		return compare(((PrimitiveArrayComparator<T, C>) referencedComparator).reference, this.reference);
	}

	@Override
	public int compareSerialized(DataInputView firstSource, DataInputView secondSource) throws IOException {
		int firstCount = firstSource.readInt();
		int secondCount = secondSource.readInt();
		for (int x = 0; x < min(firstCount, secondCount); x++) {
			int cmp = comparator.compareSerialized(firstSource, secondSource);
			if (cmp != 0) {
				return cmp;
			}
		}
		int cmp = firstCount - secondCount;
		return ascending ? cmp : -cmp;
	}

	@Override
	public int extractKeys(Object record, Object[] target, int index) {
		target[index] = record;
		return 1;
	}

	@Override
	public TypeComparator[] getFlatComparators() {
		return comparators;
	}

	@Override
	public boolean supportsNormalizedKey() {
		return false;
	}

	@Override
	public boolean supportsSerializationWithKeyNormalization() {
		return false;
	}

	@Override
	public int getNormalizeKeyLen() {
		return 0;
	}

	@Override
	public boolean isNormalizedKeyPrefixOnly(int keyBytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putNormalizedKey(T record, MemorySegment target, int offset, int numBytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeWithKeyNormalization(T record, DataOutputView target) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public T readWithKeyDenormalization(T reuse, DataInputView source) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean invertNormalizedKey() {
		return !ascending;
	}
}

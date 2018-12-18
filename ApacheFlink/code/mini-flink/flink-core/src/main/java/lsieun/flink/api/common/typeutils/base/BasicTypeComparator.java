package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;


@Internal
public abstract class BasicTypeComparator<T extends Comparable<T>> extends TypeComparator<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private transient T reference;
	
	protected final boolean ascendingComparison;

	// For use by getComparators
	@SuppressWarnings("rawtypes")
	private final TypeComparator[] comparators = new TypeComparator[] {this};
	

	protected BasicTypeComparator(boolean ascending) {
		this.ascendingComparison = ascending;
	}

	@Override
	public int hash(T value) {
		return value.hashCode();
	}

	@Override
	public void setReference(T toCompare) {
		this.reference = toCompare;
	}

	@Override
	public boolean equalToReference(T candidate) {
		return candidate.equals(reference);
	}

	@Override
	public int compareToReference(TypeComparator<T> referencedComparator) {
		int comp = ((BasicTypeComparator<T>) referencedComparator).reference.compareTo(reference);
		return ascendingComparison ? comp : -comp;
	}

	@Override
	public int compare(T first, T second) {
		int cmp = first.compareTo(second);
		return ascendingComparison ? cmp : -cmp;
	}

	@Override
	public boolean invertNormalizedKey() {
		return !ascendingComparison;
	}
	
	@Override
	public boolean supportsSerializationWithKeyNormalization() {
		return false;
	}

	@Override
	public void writeWithKeyNormalization(T record, DataOutputView target) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int extractKeys(Object record, Object[] target, int index) {
		target[index] = record;
		return 1;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TypeComparator[] getFlatComparators() {
		return comparators;
	}

	@Override
	public T readWithKeyDenormalization(T reuse, DataInputView source) throws IOException {
		throw new UnsupportedOperationException();
	}
}

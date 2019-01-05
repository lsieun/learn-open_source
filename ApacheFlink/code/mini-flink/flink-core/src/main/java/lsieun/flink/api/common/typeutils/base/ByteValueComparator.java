package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.core.memory.MemorySegment;
import lsieun.flink.types.ByteValue;
import lsieun.flink.types.NormalizableKey;

/**
 * Specialized comparator for ByteValue based on CopyableValueComparator.
 */
@Internal
public class ByteValueComparator extends TypeComparator<ByteValue> {

	private static final long serialVersionUID = 1L;

	private final boolean ascendingComparison;

	private final ByteValue reference = new ByteValue();

	private final ByteValue tempReference = new ByteValue();

	private final TypeComparator<?>[] comparators = new TypeComparator[] {this};

	public ByteValueComparator(boolean ascending) {
		this.ascendingComparison = ascending;
	}

	@Override
	public int hash(ByteValue record) {
		return record.hashCode();
	}

	@Override
	public void setReference(ByteValue toCompare) {
		toCompare.copyTo(reference);
	}

	@Override
	public boolean equalToReference(ByteValue candidate) {
		return candidate.equals(this.reference);
	}

	@Override
	public int compareToReference(TypeComparator<ByteValue> referencedComparator) {
		ByteValue otherRef = ((ByteValueComparator) referencedComparator).reference;
		int comp = otherRef.compareTo(reference);
		return ascendingComparison ? comp : -comp;
	}

	@Override
	public int compare(ByteValue first, ByteValue second) {
		int comp = first.compareTo(second);
		return ascendingComparison ? comp : -comp;
	}

	@Override
	public int compareSerialized(DataInputView firstSource, DataInputView secondSource) throws IOException {
		reference.read(firstSource);
		tempReference.read(secondSource);
		int comp = reference.compareTo(tempReference);
		return ascendingComparison ? comp : -comp;
	}

	@Override
	public boolean supportsNormalizedKey() {
		return NormalizableKey.class.isAssignableFrom(ByteValue.class);
	}

	@Override
	public int getNormalizeKeyLen() {
		return reference.getMaxNormalizedKeyLen();
	}

	@Override
	public boolean isNormalizedKeyPrefixOnly(int keyBytes) {
		return keyBytes < getNormalizeKeyLen();
	}

	@Override
	public void putNormalizedKey(ByteValue record, MemorySegment target, int offset, int numBytes) {
		record.copyNormalizedKey(target, offset, numBytes);
	}

	@Override
	public boolean invertNormalizedKey() {
		return !ascendingComparison;
	}

	@Override
	public TypeComparator<ByteValue> duplicate() {
		return new ByteValueComparator(ascendingComparison);
	}

	@Override
	public int extractKeys(Object record, Object[] target, int index) {
		target[index] = record;
		return 1;
	}

	@Override
	public TypeComparator<?>[] getFlatComparators() {
		return comparators;
	}

	// --------------------------------------------------------------------------------------------
	// unsupported normalization
	// --------------------------------------------------------------------------------------------

	@Override
	public boolean supportsSerializationWithKeyNormalization() {
		return false;
	}

	@Override
	public void writeWithKeyNormalization(ByteValue record, DataOutputView target) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteValue readWithKeyDenormalization(ByteValue reuse, DataInputView source) throws IOException {
		throw new UnsupportedOperationException();
	}
}
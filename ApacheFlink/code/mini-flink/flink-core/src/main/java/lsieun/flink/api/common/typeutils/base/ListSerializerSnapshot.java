package lsieun.flink.api.common.typeutils.base;

import static lsieun.flink.util.Preconditions.checkState;

import java.io.IOException;
import java.util.List;

import lsieun.flink.api.common.typeutils.CompositeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializer;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.util.Preconditions;

/**
 * Snapshot class for the {@link ListSerializer}.
 */
public class ListSerializerSnapshot<T> implements TypeSerializerSnapshot<List<T>> {

	private static final int CURRENT_VERSION = 1;

	private CompositeSerializerSnapshot nestedElementSerializerSnapshot;

	/**
	 * Constructor for read instantiation.
	 */
	public ListSerializerSnapshot() {}

	/**
	 * Constructor to create the snapshot for writing.
	 */
	public ListSerializerSnapshot(TypeSerializer<T> elementSerializer) {
		this.nestedElementSerializerSnapshot = new CompositeSerializerSnapshot(Preconditions.checkNotNull(elementSerializer));
	}

	@Override
	public int getCurrentVersion() {
		return CURRENT_VERSION;
	}

	@Override
	public TypeSerializer<List<T>> restoreSerializer() {
		return new ListSerializer<>(nestedElementSerializerSnapshot.getRestoreSerializer(0));
	}

	@Override
	public TypeSerializerSchemaCompatibility<List<T>> resolveSchemaCompatibility(TypeSerializer<List<T>> newSerializer) {
		checkState(nestedElementSerializerSnapshot != null);

		if (newSerializer instanceof ListSerializer) {
			ListSerializer<T> serializer = (ListSerializer<T>) newSerializer;

			return nestedElementSerializerSnapshot.resolveCompatibilityWithNested(
				TypeSerializerSchemaCompatibility.compatibleAsIs(),
				serializer.getElementSerializer());
		}
		else {
			return TypeSerializerSchemaCompatibility.incompatible();
		}
	}

	@Override
	public void writeSnapshot(DataOutputView out) throws IOException {
		nestedElementSerializerSnapshot.writeCompositeSnapshot(out);
	}

	@Override
	public void readSnapshot(int readVersion, DataInputView in, ClassLoader userCodeClassLoader) throws IOException {
		this.nestedElementSerializerSnapshot = CompositeSerializerSnapshot.readCompositeSnapshot(in, userCodeClassLoader);
	}
}

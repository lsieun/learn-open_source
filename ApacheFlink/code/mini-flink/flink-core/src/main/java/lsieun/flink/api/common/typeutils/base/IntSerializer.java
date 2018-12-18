package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * Type serializer for {@code Integer} (and {@code int}, via auto-boxing).
 */
@Internal
public final class IntSerializer extends TypeSerializerSingleton<Integer> {

	private static final long serialVersionUID = 1L;

	/** Sharable instance of the IntSerializer. */
	public static final IntSerializer INSTANCE = new IntSerializer();

	private static final Integer ZERO = 0;

	@Override
	public boolean isImmutableType() {
		return true;
	}

	@Override
	public Integer createInstance() {
		return ZERO;
	}

	@Override
	public Integer copy(Integer from) {
		return from;
	}

	@Override
	public Integer copy(Integer from, Integer reuse) {
		return from;
	}

	@Override
	public int getLength() {
		return 4;
	}

	@Override
	public void serialize(Integer record, DataOutputView target) throws IOException {
		target.writeInt(record);
	}

	@Override
	public Integer deserialize(DataInputView source) throws IOException {
		return source.readInt();
	}

	@Override
	public Integer deserialize(Integer reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeInt(source.readInt());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof IntSerializer;
	}

	@Override
	public TypeSerializerSnapshot<Integer> snapshotConfiguration() {
		return new IntSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class IntSerializerSnapshot extends SimpleTypeSerializerSnapshot<Integer> {

		public IntSerializerSnapshot() {
			super(IntSerializer.class);
		}
	}
}

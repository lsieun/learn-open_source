package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * Type serializer for {@code Boolean}.
 */
@Internal
public final class BooleanSerializer extends TypeSerializerSingleton<Boolean> {

	private static final long serialVersionUID = 1L;

	/** Sharable instance of the BooleanSerializer. */
	public static final BooleanSerializer INSTANCE = new BooleanSerializer();

	private static final Boolean FALSE = Boolean.FALSE;

	@Override
	public boolean isImmutableType() {
		return true;
	}

	@Override
	public Boolean createInstance() {
		return FALSE;
	}

	@Override
	public Boolean copy(Boolean from) {
		return from;
	}

	@Override
	public Boolean copy(Boolean from, Boolean reuse) {
		return from;
	}

	@Override
	public int getLength() {
		return 1;
	}

	@Override
	public void serialize(Boolean record, DataOutputView target) throws IOException {
		target.writeBoolean(record);
	}

	@Override
	public Boolean deserialize(DataInputView source) throws IOException {
		return source.readBoolean();
	}

	@Override
	public Boolean deserialize(Boolean reuse, DataInputView source) throws IOException {
		return source.readBoolean();
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeBoolean(source.readBoolean());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof BooleanSerializer;
	}

	@Override
	public TypeSerializerSnapshot<Boolean> snapshotConfiguration() {
		return new BooleanSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class BooleanSerializerSnapshot extends SimpleTypeSerializerSnapshot<Boolean> {

		public BooleanSerializerSnapshot() {
			super(BooleanSerializer.class);
		}
	}
}

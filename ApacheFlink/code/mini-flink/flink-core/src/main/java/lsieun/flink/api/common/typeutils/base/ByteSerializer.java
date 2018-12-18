package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * Type serializer for {@code Byte}.
 */
@Internal
public final class ByteSerializer extends TypeSerializerSingleton<Byte> {

	private static final long serialVersionUID = 1L;

	/** Sharable instance of the ByteSerializer. */
	public static final ByteSerializer INSTANCE = new ByteSerializer();

	private static final Byte ZERO = (byte) 0;

	@Override
	public boolean isImmutableType() {
		return true;
	}

	@Override
	public Byte createInstance() {
		return ZERO;
	}

	@Override
	public Byte copy(Byte from) {
		return from;
	}

	@Override
	public Byte copy(Byte from, Byte reuse) {
		return from;
	}

	@Override
	public int getLength() {
		return 1;
	}

	@Override
	public void serialize(Byte record, DataOutputView target) throws IOException {
		target.writeByte(record);
	}

	@Override
	public Byte deserialize(DataInputView source) throws IOException {
		return source.readByte();
	}

	@Override
	public Byte deserialize(Byte reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeByte(source.readByte());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof ByteSerializer;
	}

	@Override
	public TypeSerializerSnapshot<Byte> snapshotConfiguration() {
		return new ByteSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class ByteSerializerSnapshot extends SimpleTypeSerializerSnapshot<Byte> {

		public ByteSerializerSnapshot() {
			super(ByteSerializer.class);
		}
	}
}

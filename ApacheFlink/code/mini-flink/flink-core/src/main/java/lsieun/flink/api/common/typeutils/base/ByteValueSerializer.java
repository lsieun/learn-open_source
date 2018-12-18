package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.types.ByteValue;

@Internal
public final class ByteValueSerializer extends TypeSerializerSingleton<ByteValue> {

	private static final long serialVersionUID = 1L;
	
	public static final ByteValueSerializer INSTANCE = new ByteValueSerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public ByteValue createInstance() {
		return new ByteValue();
	}

	@Override
	public ByteValue copy(ByteValue from) {
		return copy(from, new ByteValue());
	}
	
	@Override
	public ByteValue copy(ByteValue from, ByteValue reuse) {
		reuse.setValue(from.getValue());
		return reuse;
	}

	@Override
	public int getLength() {
		return 1;
	}

	@Override
	public void serialize(ByteValue record, DataOutputView target) throws IOException {
		record.write(target);
	}

	@Override
	public ByteValue deserialize(DataInputView source) throws IOException {
		return deserialize(new ByteValue(), source);
	}
	
	@Override
	public ByteValue deserialize(ByteValue reuse, DataInputView source) throws IOException {
		reuse.read(source);
		return reuse;
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeByte(source.readByte());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof ByteValueSerializer;
	}

	@Override
	public TypeSerializerSnapshot<ByteValue> snapshotConfiguration() {
		return new ByteValueSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class ByteValueSerializerSnapshot extends SimpleTypeSerializerSnapshot<ByteValue> {

		public ByteValueSerializerSnapshot() {
			super(ByteValueSerializer.class);
		}
	}
}

package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.types.IntValue;

@Internal
public final class IntValueSerializer extends TypeSerializerSingleton<IntValue> {

	private static final long serialVersionUID = 1L;
	
	public static final IntValueSerializer INSTANCE = new IntValueSerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public IntValue createInstance() {
		return new IntValue();
	}

	@Override
	public IntValue copy(IntValue from) {
		return copy(from, new IntValue());
	}
	
	@Override
	public IntValue copy(IntValue from, IntValue reuse) {
		reuse.setValue(from.getValue());
		return reuse;
	}

	@Override
	public int getLength() {
		return 4;
	}

	@Override
	public void serialize(IntValue record, DataOutputView target) throws IOException {
		record.write(target);
	}

	@Override
	public IntValue deserialize(DataInputView source) throws IOException {
		return deserialize(new IntValue(), source);
	}
	
	@Override
	public IntValue deserialize(IntValue reuse, DataInputView source) throws IOException {
		reuse.read(source);
		return reuse;
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeInt(source.readInt());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof IntValueSerializer;
	}

	@Override
	public TypeSerializerSnapshot<IntValue> snapshotConfiguration() {
		return new IntValueSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class IntValueSerializerSnapshot extends SimpleTypeSerializerSnapshot<IntValue> {

		public IntValueSerializerSnapshot() {
			super(IntValueSerializer.class);
		}
	}
}

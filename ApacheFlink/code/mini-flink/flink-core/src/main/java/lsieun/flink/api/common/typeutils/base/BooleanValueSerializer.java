package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.types.BooleanValue;

@Internal
public final class BooleanValueSerializer extends TypeSerializerSingleton<BooleanValue> {

	private static final long serialVersionUID = 1L;
	
	public static final BooleanValueSerializer INSTANCE = new BooleanValueSerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public BooleanValue createInstance() {
		return new BooleanValue();
	}

	@Override
	public BooleanValue copy(BooleanValue from) {
		BooleanValue result = new BooleanValue();
		result.setValue(from.getValue());
		return result;
	}
	
	@Override
	public BooleanValue copy(BooleanValue from, BooleanValue reuse) {
		reuse.setValue(from.getValue());
		return reuse;
	}

	@Override
	public int getLength() {
		return 1;
	}

	@Override
	public void serialize(BooleanValue record, DataOutputView target) throws IOException {
		record.write(target);
	}

	@Override
	public BooleanValue deserialize(DataInputView source) throws IOException {
		return deserialize(new BooleanValue(), source);
	}
	
	@Override
	public BooleanValue deserialize(BooleanValue reuse, DataInputView source) throws IOException {
		reuse.read(source);
		return reuse;
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeBoolean(source.readBoolean());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof BooleanValueSerializer;
	}

	@Override
	public TypeSerializerSnapshot<BooleanValue> snapshotConfiguration() {
		return new BooleanValueSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class BooleanValueSerializerSnapshot extends SimpleTypeSerializerSnapshot<BooleanValue> {

		public BooleanValueSerializerSnapshot() {
			super(BooleanValueSerializer.class);
		}
	}
}

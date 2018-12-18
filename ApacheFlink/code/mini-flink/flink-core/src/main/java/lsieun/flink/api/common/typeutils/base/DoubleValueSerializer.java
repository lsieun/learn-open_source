package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.types.DoubleValue;

@Internal
public final class DoubleValueSerializer extends TypeSerializerSingleton<DoubleValue> {

	private static final long serialVersionUID = 1L;
	
	public static final DoubleValueSerializer INSTANCE = new DoubleValueSerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public DoubleValue createInstance() {
		return new DoubleValue();
	}

	@Override
	public DoubleValue copy(DoubleValue from) {
		return copy(from, new DoubleValue());
	}
	
	@Override
	public DoubleValue copy(DoubleValue from, DoubleValue reuse) {
		reuse.setValue(from.getValue());
		return reuse;
	}

	@Override
	public int getLength() {
		return 8;
	}

	@Override
	public void serialize(DoubleValue record, DataOutputView target) throws IOException {
		record.write(target);
	}

	@Override
	public DoubleValue deserialize(DataInputView source) throws IOException {
		return deserialize(new DoubleValue(), source);
	}
	
	@Override
	public DoubleValue deserialize(DoubleValue reuse, DataInputView source) throws IOException {
		reuse.read(source);
		return reuse;
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeDouble(source.readDouble());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof DoubleValueSerializer;
	}

	@Override
	public TypeSerializerSnapshot<DoubleValue> snapshotConfiguration() {
		return new DoubleValueSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class DoubleValueSerializerSnapshot extends SimpleTypeSerializerSnapshot<DoubleValue> {

		public DoubleValueSerializerSnapshot() {
			super(DoubleValueSerializer.class);
		}
	}
}

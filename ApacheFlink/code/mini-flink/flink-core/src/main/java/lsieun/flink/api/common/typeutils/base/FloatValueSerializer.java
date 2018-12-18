package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.types.FloatValue;

@Internal
public class FloatValueSerializer extends TypeSerializerSingleton<FloatValue> {

	private static final long serialVersionUID = 1L;
	
	public static final FloatValueSerializer INSTANCE = new FloatValueSerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public FloatValue createInstance() {
		return new FloatValue();
	}

	@Override
	public FloatValue copy(FloatValue from) {
		return copy(from, new FloatValue());
	}
	
	@Override
	public FloatValue copy(FloatValue from, FloatValue reuse) {
		reuse.setValue(from.getValue());
		return reuse;
	}

	@Override
	public int getLength() {
		return 4;
	}

	@Override
	public void serialize(FloatValue record, DataOutputView target) throws IOException {
		record.write(target);
	}

	@Override
	public FloatValue deserialize(DataInputView source) throws IOException {
		return deserialize(new FloatValue(), source);
	}
	
	@Override
	public FloatValue deserialize(FloatValue reuse, DataInputView source) throws IOException {
		reuse.read(source);
		return reuse;
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeFloat(source.readFloat());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof FloatValueSerializer;
	}

	@Override
	public TypeSerializerSnapshot<FloatValue> snapshotConfiguration() {
		return new FloatValueSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class FloatValueSerializerSnapshot extends SimpleTypeSerializerSnapshot<FloatValue> {

		public FloatValueSerializerSnapshot() {
			super(FloatValueSerializer.class);
		}
	}
}

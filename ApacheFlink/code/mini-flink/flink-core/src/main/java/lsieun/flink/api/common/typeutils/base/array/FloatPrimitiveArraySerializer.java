package lsieun.flink.api.common.typeutils.base.array;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.base.TypeSerializerSingleton;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * A serializer for float arrays.
 */
@Internal
public final class FloatPrimitiveArraySerializer extends TypeSerializerSingleton<float[]>{

	private static final long serialVersionUID = 1L;
	
	private static final float[] EMPTY = new float[0];

	public static final FloatPrimitiveArraySerializer INSTANCE = new FloatPrimitiveArraySerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public float[] createInstance() {
		return EMPTY;
	}

	@Override
	public float[] copy(float[] from) {
		float[] copy = new float[from.length];
		System.arraycopy(from, 0, copy, 0, from.length);
		return copy;
	}
	
	@Override
	public float[] copy(float[] from, float[] reuse) {
		return copy(from);
	}

	@Override
	public int getLength() {
		return -1;
	}


	@Override
	public void serialize(float[] record, DataOutputView target) throws IOException {
		if (record == null) {
			throw new IllegalArgumentException("The record must not be null.");
		}
		
		final int len = record.length;
		target.writeInt(len);
		for (int i = 0; i < len; i++) {
			target.writeFloat(record[i]);
		}
	}

	@Override
	public float[] deserialize(DataInputView source) throws IOException {
		final int len = source.readInt();
		float[] result = new float[len];
		
		for (int i = 0; i < len; i++) {
			result[i] = source.readFloat();
		}
		
		return result;
	}
	
	@Override
	public float[] deserialize(float[] reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		final int len = source.readInt();
		target.writeInt(len);
		target.write(source, len * 4);
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof FloatPrimitiveArraySerializer;
	}

	@Override
	public TypeSerializerSnapshot<float[]> snapshotConfiguration() {
		return new FloatPrimitiveArraySerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class FloatPrimitiveArraySerializerSnapshot extends SimpleTypeSerializerSnapshot<float[]> {

		public FloatPrimitiveArraySerializerSnapshot() {
			super(FloatPrimitiveArraySerializer.class);
		}
	}
}

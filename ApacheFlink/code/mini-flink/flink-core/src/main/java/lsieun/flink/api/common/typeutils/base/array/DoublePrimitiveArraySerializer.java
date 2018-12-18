package lsieun.flink.api.common.typeutils.base.array;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.base.TypeSerializerSingleton;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * A serializer for double arrays.
 */
@Internal
public final class DoublePrimitiveArraySerializer extends TypeSerializerSingleton<double[]>{

	private static final long serialVersionUID = 1L;
	
	private static final double[] EMPTY = new double[0];

	public static final DoublePrimitiveArraySerializer INSTANCE = new DoublePrimitiveArraySerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public double[] createInstance() {
		return EMPTY;
	}
	
	@Override
	public double[] copy(double[] from) {
		double[] copy = new double[from.length];
		System.arraycopy(from, 0, copy, 0, from.length);
		return copy;
	}

	@Override
	public double[] copy(double[] from, double[] reuse) {
		return copy(from);
	}

	@Override
	public int getLength() {
		return -1;
	}


	@Override
	public void serialize(double[] record, DataOutputView target) throws IOException {
		if (record == null) {
			throw new IllegalArgumentException("The record must not be null.");
		}
		
		final int len = record.length;
		target.writeInt(len);
		for (int i = 0; i < len; i++) {
			target.writeDouble(record[i]);
		}
	}

	@Override
	public double[] deserialize(DataInputView source) throws IOException {
		final int len = source.readInt();
		double[] result = new double[len];
		
		for (int i = 0; i < len; i++) {
			result[i] = source.readDouble();
		}
		
		return result;
	}
	
	@Override
	public double[] deserialize(double[] reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		final int len = source.readInt();
		target.writeInt(len);
		target.write(source, len * 8);
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof DoublePrimitiveArraySerializer;
	}

	@Override
	public TypeSerializerSnapshot<double[]> snapshotConfiguration() {
		return new DoublePrimitiveArraySerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class DoublePrimitiveArraySerializerSnapshot extends SimpleTypeSerializerSnapshot<double[]> {

		public DoublePrimitiveArraySerializerSnapshot() {
			super(DoublePrimitiveArraySerializer.class);
		}
	}
}

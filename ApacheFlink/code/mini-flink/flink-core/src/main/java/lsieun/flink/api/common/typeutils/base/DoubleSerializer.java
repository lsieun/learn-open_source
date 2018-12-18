package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * Type serializer for {@code Double}.
 */
@Internal
public final class DoubleSerializer extends TypeSerializerSingleton<Double> {

	private static final long serialVersionUID = 1L;

	/** Sharable instance of the DoubleSerializer. */
	public static final DoubleSerializer INSTANCE = new DoubleSerializer();

	private static final Double ZERO = 0.0;

	@Override
	public boolean isImmutableType() {
		return true;
	}

	@Override
	public Double createInstance() {
		return ZERO;
	}

	@Override
	public Double copy(Double from) {
		return from;
	}

	@Override
	public Double copy(Double from, Double reuse) {
		return from;
	}

	@Override
	public int getLength() {
		return 8;
	}

	@Override
	public void serialize(Double record, DataOutputView target) throws IOException {
		target.writeDouble(record);
	}

	@Override
	public Double deserialize(DataInputView source) throws IOException {
		return source.readDouble();
	}

	@Override
	public Double deserialize(Double reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeDouble(source.readDouble());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof DoubleSerializer;
	}

	@Override
	public TypeSerializerSnapshot<Double> snapshotConfiguration() {
		return new DoubleSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class DoubleSerializerSnapshot extends SimpleTypeSerializerSnapshot<Double> {

		public DoubleSerializerSnapshot() {
			super(DoubleSerializer.class);
		}
	}
}

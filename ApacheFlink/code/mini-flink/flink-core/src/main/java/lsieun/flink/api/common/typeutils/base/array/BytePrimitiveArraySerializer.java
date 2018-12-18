package lsieun.flink.api.common.typeutils.base.array;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.base.TypeSerializerSingleton;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * A serializer for byte arrays.
 */
@Internal
public final class BytePrimitiveArraySerializer extends TypeSerializerSingleton<byte[]>{

	private static final long serialVersionUID = 1L;
	
	private static final byte[] EMPTY = new byte[0];

	public static final BytePrimitiveArraySerializer INSTANCE = new BytePrimitiveArraySerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public byte[] createInstance() {
		return EMPTY;
	}

	@Override
	public byte[] copy(byte[] from) {
		byte[] copy = new byte[from.length];
		System.arraycopy(from, 0, copy, 0, from.length);
		return copy;
	}
	
	@Override
	public byte[] copy(byte[] from, byte[] reuse) {
		return copy(from);
	}

	@Override
	public int getLength() {
		return -1;
	}


	@Override
	public void serialize(byte[] record, DataOutputView target) throws IOException {
		if (record == null) {
			throw new IllegalArgumentException("The record must not be null.");
		}
		
		final int len = record.length;
		target.writeInt(len);
		target.write(record);
	}

	@Override
	public byte[] deserialize(DataInputView source) throws IOException {
		final int len = source.readInt();
		byte[] result = new byte[len];
		source.readFully(result);
		return result;
	}
	
	@Override
	public byte[] deserialize(byte[] reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		final int len = source.readInt();
		target.writeInt(len);
		target.write(source, len);
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof BytePrimitiveArraySerializer;
	}

	@Override
	public TypeSerializerSnapshot<byte[]> snapshotConfiguration() {
		return new BytePrimitiveArraySerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class BytePrimitiveArraySerializerSnapshot extends SimpleTypeSerializerSnapshot<byte[]> {

		public BytePrimitiveArraySerializerSnapshot() {
			super(BytePrimitiveArraySerializer.class);
		}
	}
}

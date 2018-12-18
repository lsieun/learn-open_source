package lsieun.flink.api.common.typeutils.base.array;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.base.TypeSerializerSingleton;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * A serializer for long arrays.
 */
@Internal
public final class LongPrimitiveArraySerializer extends TypeSerializerSingleton<long[]>{

	private static final long serialVersionUID = 1L;
	
	private static final long[] EMPTY = new long[0];

	public static final LongPrimitiveArraySerializer INSTANCE = new LongPrimitiveArraySerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public long[] createInstance() {
		return EMPTY;
	}

	@Override
	public long[] copy(long[] from) {
		long[] result = new long[from.length];
		System.arraycopy(from, 0, result, 0, from.length);
		return result;
	}
	
	@Override
	public long[] copy(long[] from, long[] reuse) {
		return copy(from);
	}

	@Override
	public int getLength() {
		return -1;
	}


	@Override
	public void serialize(long[] record, DataOutputView target) throws IOException {
		if (record == null) {
			throw new IllegalArgumentException("The record must not be null.");
		}
		
		final int len = record.length;
		target.writeInt(len);
		for (int i = 0; i < len; i++) {
			target.writeLong(record[i]);
		}
	}

	@Override
	public long[] deserialize(DataInputView source) throws IOException {
		final int len = source.readInt();
		long[] array = new long[len];
		
		for (int i = 0; i < len; i++) {
			array[i] = source.readLong();
		}
		
		return array;
	}
	
	@Override
	public long[] deserialize(long[] reuse, DataInputView source) throws IOException {
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
		return obj instanceof LongPrimitiveArraySerializer;
	}

	@Override
	public TypeSerializerSnapshot<long[]> snapshotConfiguration() {
		return new LongPrimitiveArraySerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class LongPrimitiveArraySerializerSnapshot extends SimpleTypeSerializerSnapshot<long[]> {

		public LongPrimitiveArraySerializerSnapshot() {
			super(LongPrimitiveArraySerializer.class);
		}
	}
}

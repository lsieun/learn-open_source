package lsieun.flink.api.common.typeutils.base.array;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.base.TypeSerializerSingleton;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * A serializer for boolean arrays.
 */
@Internal
public final class BooleanPrimitiveArraySerializer extends TypeSerializerSingleton<boolean[]>{

	private static final long serialVersionUID = 1L;
	
	private static final boolean[] EMPTY = new boolean[0];

	public static final BooleanPrimitiveArraySerializer INSTANCE = new BooleanPrimitiveArraySerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}
	
	@Override
	public boolean[] createInstance() {
		return EMPTY;
	}

	@Override
	public boolean[] copy(boolean[] from) {
		boolean[] copy = new boolean[from.length];
		System.arraycopy(from, 0, copy, 0, from.length);
		return copy;
	}

	@Override
	public boolean[] copy(boolean[] from, boolean[] reuse) {
		return copy(from);
	}

	@Override
	public int getLength() {
		return -1;
	}


	@Override
	public void serialize(boolean[] record, DataOutputView target) throws IOException {
		if (record == null) {
			throw new IllegalArgumentException("The record must not be null.");
		}
		
		final int len = record.length;
		target.writeInt(len);
		for (int i = 0; i < len; i++) {
			target.writeBoolean(record[i]);
		}
	}


	@Override
	public boolean[] deserialize(DataInputView source) throws IOException {
		final int len = source.readInt();
		boolean[] result = new boolean[len];
		
		for (int i = 0; i < len; i++) {
			result[i] = source.readBoolean();
		}
		
		return result;
	}
	
	@Override
	public boolean[] deserialize(boolean[] reuse, DataInputView source) throws IOException {
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
		return obj instanceof BooleanPrimitiveArraySerializer;
	}

	@Override
	public TypeSerializerSnapshot<boolean[]> snapshotConfiguration() {
		return new BooleanPrimitiveArraySerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class BooleanPrimitiveArraySerializerSnapshot extends SimpleTypeSerializerSnapshot<boolean[]> {

		public BooleanPrimitiveArraySerializerSnapshot() {
			super(BooleanPrimitiveArraySerializer.class);
		}
	}
}

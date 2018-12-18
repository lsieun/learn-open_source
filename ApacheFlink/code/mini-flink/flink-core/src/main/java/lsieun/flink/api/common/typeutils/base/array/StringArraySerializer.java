package lsieun.flink.api.common.typeutils.base.array;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.base.TypeSerializerSingleton;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.types.StringValue;


/**
 * A serializer for String arrays. Specialized for efficiency.
 */
@Internal
public final class StringArraySerializer extends TypeSerializerSingleton<String[]>{

	private static final long serialVersionUID = 1L;
	
	private static final String[] EMPTY = new String[0];
	
	public static final StringArraySerializer INSTANCE = new StringArraySerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public String[] createInstance() {
		return EMPTY;
	}

	@Override
	public String[] copy(String[] from) {
		String[] target = new String[from.length];
		System.arraycopy(from, 0, target, 0, from.length);
		return target;
	}
	
	@Override
	public String[] copy(String[] from, String[] reuse) {
		return copy(from);
	}

	@Override
	public int getLength() {
		return -1;
	}

	@Override
	public void serialize(String[] record, DataOutputView target) throws IOException {
		if (record == null) {
			throw new IllegalArgumentException("The record must not be null.");
		}
		
		final int len = record.length;
		target.writeInt(len);
		for (int i = 0; i < len; i++) {
			StringValue.writeString(record[i], target);
		}
	}

	@Override
	public String[] deserialize(DataInputView source) throws IOException {
		final int len = source.readInt();
		String[] array = new String[len];
		
		for (int i = 0; i < len; i++) {
			array[i] = StringValue.readString(source);
		}
		
		return array;
	}
	
	@Override
	public String[] deserialize(String[] reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		final int len = source.readInt();
		target.writeInt(len);
		
		for (int i = 0; i < len; i++) {
			StringValue.copyString(source, target);
		}
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof StringArraySerializer;
	}

	@Override
	public TypeSerializerSnapshot<String[]> snapshotConfiguration() {
		return new StringArraySerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class StringArraySerializerSnapshot extends SimpleTypeSerializerSnapshot<String[]> {
		public StringArraySerializerSnapshot() {
			super(StringArraySerializer.class);
		}
	}
}

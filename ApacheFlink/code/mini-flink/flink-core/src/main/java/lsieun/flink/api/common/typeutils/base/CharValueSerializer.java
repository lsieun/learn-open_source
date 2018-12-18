package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.types.CharValue;

@Internal
public class CharValueSerializer extends TypeSerializerSingleton<CharValue> {

	private static final long serialVersionUID = 1L;
	
	public static final CharValueSerializer INSTANCE = new CharValueSerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public CharValue createInstance() {
		return new CharValue();
	}
	
	@Override
	public CharValue copy(CharValue from) {
		return copy(from, new CharValue());
	}

	@Override
	public CharValue copy(CharValue from, CharValue reuse) {
		reuse.setValue(from.getValue());
		return reuse;
	}

	@Override
	public int getLength() {
		return 2;
	}

	@Override
	public void serialize(CharValue record, DataOutputView target) throws IOException {
		record.write(target);
	}
	
	@Override
	public CharValue deserialize(DataInputView source) throws IOException {
		return deserialize(new CharValue(), source);
	}

	@Override
	public CharValue deserialize(CharValue reuse, DataInputView source) throws IOException {
		reuse.read(source);
		return reuse;
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeChar(source.readChar());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof CharValueSerializer;
	}

	@Override
	public TypeSerializerSnapshot<CharValue> snapshotConfiguration() {
		return new CharValueSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class CharValueSerializerSnapshot extends SimpleTypeSerializerSnapshot<CharValue> {

		public CharValueSerializerSnapshot() {
			super(CharValueSerializer.class);
		}
	}
}

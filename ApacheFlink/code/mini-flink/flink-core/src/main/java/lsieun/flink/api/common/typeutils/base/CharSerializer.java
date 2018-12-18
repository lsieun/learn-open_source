package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * Type serializer for {@code Character}.
 */
@Internal
public final class CharSerializer extends TypeSerializerSingleton<Character> {

	private static final long serialVersionUID = 1L;

	/** Sharable instance of the CharSerializer. */
	public static final CharSerializer INSTANCE = new CharSerializer();

	private static final Character ZERO = (char) 0;

	@Override
	public boolean isImmutableType() {
		return true;
	}

	@Override
	public Character createInstance() {
		return ZERO;
	}

	@Override
	public Character copy(Character from) {
		return from;
	}

	@Override
	public Character copy(Character from, Character reuse) {
		return from;
	}

	@Override
	public int getLength() {
		return 2;
	}

	@Override
	public void serialize(Character record, DataOutputView target) throws IOException {
		target.writeChar(record);
	}

	@Override
	public Character deserialize(DataInputView source) throws IOException {
		return source.readChar();
	}

	@Override
	public Character deserialize(Character reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeChar(source.readChar());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof CharSerializer;
	}

	@Override
	public TypeSerializerSnapshot<Character> snapshotConfiguration() {
		return new CharSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class CharSerializerSnapshot extends SimpleTypeSerializerSnapshot<Character> {

		public CharSerializerSnapshot() {
			super(CharSerializer.class);
		}
	}
}

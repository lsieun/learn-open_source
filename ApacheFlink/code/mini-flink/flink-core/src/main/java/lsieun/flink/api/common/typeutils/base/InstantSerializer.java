package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;
import java.time.Instant;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * Serializer for serializing/deserializing Instant values including null values.
 */
@Internal
public final class InstantSerializer extends TypeSerializerSingleton<Instant> {
	static final int SECONDS_BYTES = Long.BYTES;
	static final int NANOS_BYTES = Integer.BYTES;

	private static final long NULL_SECONDS = Long.MIN_VALUE;
	//Nanos of normal Instant is between 0 and 999,999,999,
	//therefore we can use Integer.MIN_VALUE to represent NULL Instant
	//regardless supported range of seconds
	private static final int  NULL_NANOS = Integer.MIN_VALUE;

	public static final InstantSerializer INSTANCE = new InstantSerializer();

	@Override
	public boolean isImmutableType() {
		return true;
	}

	@Override
	public Instant createInstance() {
		return Instant.EPOCH;
	}

	@Override
	public Instant copy(Instant from) {
		return from;
	}

	@Override
	public Instant copy(Instant from, Instant reuse) {
		return from;
	}

	@Override
	public int getLength() {
		return SECONDS_BYTES + NANOS_BYTES;
	}

	@Override
	public void serialize(Instant record, DataOutputView target) throws IOException {
		if (record == null) {
			target.writeLong(NULL_SECONDS);
			target.writeInt(NULL_NANOS);
		} else {
			target.writeLong(record.getEpochSecond());
			target.writeInt(record.getNano());
		}
	}

	@Override
	public Instant deserialize(DataInputView source) throws IOException {
		final long seconds = source.readLong();
		final int nanos = source.readInt();
		if (seconds == NULL_SECONDS && nanos == NULL_NANOS) {
			return null;
		}
		return Instant.ofEpochSecond(seconds, nanos);
	}

	@Override
	public Instant deserialize(Instant reuse, DataInputView source) throws IOException {
		return deserialize(source);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeLong(source.readLong());
		target.writeInt(source.readInt());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof InstantSerializer;
	}

	@Override
	public TypeSerializerSnapshot<Instant> snapshotConfiguration() {
		return new InstantSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class InstantSerializerSnapshot extends SimpleTypeSerializerSnapshot<Instant> {

		public InstantSerializerSnapshot() {
			super(InstantSerializer.class);
		}
	}
}

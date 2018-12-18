package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;
import java.util.Date;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.SimpleTypeSerializerSnapshot;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

@Internal
public final class DateSerializer extends TypeSerializerSingleton<Date> {

	private static final long serialVersionUID = 1L;

	public static final DateSerializer INSTANCE = new DateSerializer();

	@Override
	public boolean isImmutableType() {
		return false;
	}

	@Override
	public Date createInstance() {
		return new Date();
	}

	@Override
	public Date copy(Date from) {
		if(from == null) {
			return null;
		}
		return new Date(from.getTime());
	}

	@Override
	public Date copy(Date from, Date reuse) {
		if (from == null) {
			return null;
		}
		reuse.setTime(from.getTime());
		return reuse;
	}

	@Override
	public int getLength() {
		return 8;
	}

	@Override
	public void serialize(Date record, DataOutputView target) throws IOException {
		if (record == null) {
			target.writeLong(Long.MIN_VALUE);
		} else {
			target.writeLong(record.getTime());
		}
	}

	@Override
	public Date deserialize(DataInputView source) throws IOException {
		final long v = source.readLong();
		if (v == Long.MIN_VALUE) {
			return null;
		} else {
			return new Date(v);
		}
	}
	
	@Override
	public Date deserialize(Date reuse, DataInputView source) throws IOException {
		final long v = source.readLong();
		if (v == Long.MIN_VALUE) {
			return null;
		}
		reuse.setTime(v);
		return reuse;
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.writeLong(source.readLong());
	}

	@Override
	public boolean canEqual(Object obj) {
		return obj instanceof DateSerializer;
	}

	@Override
	public TypeSerializerSnapshot<Date> snapshotConfiguration() {
		return new DateSerializerSnapshot();
	}

	// ------------------------------------------------------------------------

	/**
	 * Serializer configuration snapshot for compatibility and format evolution.
	 */
	public static final class DateSerializerSnapshot extends SimpleTypeSerializerSnapshot<Date> {

		public DateSerializerSnapshot() {
			super(DateSerializer.class);
		}
	}
}

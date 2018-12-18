package lsieun.flink.api.common.typeutils.base;

import java.io.IOException;
import java.time.Instant;

import lsieun.flink.annotation.Internal;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.MemorySegment;

/**
 * Comparator for comparing Java Instant.
 */
@Internal
public final class InstantComparator extends BasicTypeComparator<Instant>{

	private static final long serialVersionUID = 1L;
	private static final long SECONDS_MIN_VALUE = Instant.MIN.getEpochSecond();

	public InstantComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public int compareSerialized(DataInputView firstSource, DataInputView secondSource) throws IOException {
		final long lSeconds = firstSource.readLong();
		final long rSeconds = secondSource.readLong();
		final int comp;
		if (lSeconds == rSeconds) {
			final int lNanos = firstSource.readInt();
			final int rNanos = secondSource.readInt();
			comp = (lNanos < rNanos ? -1 : (lNanos == rNanos ? 0 : 1));
		} else {
			comp = lSeconds < rSeconds ? -1 : 1;
		}
		return ascendingComparison ? comp : -comp;
	}

	@Override
	public boolean supportsNormalizedKey() {
		return true;
	}

	@Override
	public int getNormalizeKeyLen() {
		return InstantSerializer.SECONDS_BYTES + InstantSerializer.NANOS_BYTES;
	}

	@Override
	public boolean isNormalizedKeyPrefixOnly(int keyBytes) {
		return keyBytes < getNormalizeKeyLen();
	}

	@Override
	public void putNormalizedKey(Instant record, MemorySegment target, int offset, int numBytes) {
		final int secondsBytes = InstantSerializer.SECONDS_BYTES;
		final long normalizedSeconds = record.getEpochSecond() - SECONDS_MIN_VALUE;
		if (numBytes >= secondsBytes) {
			target.putLongBigEndian(offset, normalizedSeconds);
			offset += secondsBytes;
			numBytes -= secondsBytes;

			final int nanosBytes = InstantSerializer.NANOS_BYTES;
			if (numBytes >= nanosBytes) {
				target.putIntBigEndian(offset, record.getNano());
				offset += nanosBytes;
				numBytes -= nanosBytes;
				for (int i = 0; i < numBytes; i++) {
					target.put(offset + i, (byte) 0);
				}
			} else {
				final int nanos = record.getNano();
				for (int i = 0; i < numBytes;  i++) {
					target.put(offset + i, (byte) (nanos >>> ((3 - i) << 3)));
				}
			}
		} else {
			for (int i = 0; i < numBytes; i++) {
				target.put(offset + i, (byte) (normalizedSeconds >>> ((7 - i) << 3)));
			}
		}
	}

	@Override
	public TypeComparator<Instant> duplicate() {
		return new InstantComparator(ascendingComparison);
	}
}

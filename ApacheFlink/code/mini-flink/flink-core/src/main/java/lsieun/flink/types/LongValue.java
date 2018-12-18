package lsieun.flink.types;

import java.io.IOException;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.core.memory.MemorySegment;

/**
 * Boxed serializable and comparable long integer type, representing the primitive
 * type {@code long}.
 */
@Public
public class LongValue implements NormalizableKey<LongValue>, ResettableValue<LongValue>, CopyableValue<LongValue> {
	private static final long serialVersionUID = 1L;

	private long value;

	/**
	 * Initializes the encapsulated long with 0.
	 */
	public LongValue() {
		this.value = 0;
	}

	/**
	 * Initializes the encapsulated long with the specified value. 
	 * 
	 * @param value Initial value of the encapsulated long.
	 */
	public LongValue(final long value) {
		this.value = value;
	}

	/**
	 * Returns the value of the encapsulated long.
	 * 
	 * @return The value of the encapsulated long.
	 */
	public long getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the encapsulated long to the specified value.
	 * 
	 * @param value
	 *        The new value of the encapsulated long.
	 */
	public void setValue(final long value) {
		this.value = value;
	}

	@Override
	public void setValue(LongValue value) {
		this.value = value.value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	// --------------------------------------------------------------------------------------------
	
	@Override
	public void read(final DataInputView in) throws IOException {
		this.value = in.readLong();
	}

	@Override
	public void write(final DataOutputView out) throws IOException {
		out.writeLong(this.value);
	}

	// --------------------------------------------------------------------------------------------
	
	@Override
	public int compareTo(LongValue o) {
		final long other = o.value;
		return this.value < other ? -1 : this.value > other ? 1 : 0;
	}

	@Override
	public int hashCode() {
		return 43 + (int) (this.value ^ this.value >>> 32);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LongValue) {
			return ((LongValue) obj).value == this.value;
		}
		return false;
	}
	
	// --------------------------------------------------------------------------------------------
	

	@Override
	public int getMaxNormalizedKeyLen()
	{
		return 8;
	}

	@Override
	public void copyNormalizedKey(MemorySegment target, int offset, int len) {
		// see IntValue for an explanation of the logic
		if (len == 8) {
			// default case, full normalized key
			target.putLongBigEndian(offset, value - Long.MIN_VALUE);
		}
		else if (len <= 0) {
		}
		else if (len < 8) {
			long value = this.value - Long.MIN_VALUE;
			for (int i = 0; len > 0; len--, i++) {
				target.put(offset + i, (byte) (value >>> ((7-i)<<3)));
			}
		}
		else {
			target.putLongBigEndian(offset, value - Long.MIN_VALUE);
			for (int i = 8; i < len; i++) {
				target.put(offset + i, (byte) 0);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public int getBinaryLength() {
		return 8;
	}

	@Override
	public void copyTo(LongValue target) {
		target.value = this.value;
	}

	@Override
	public LongValue copy() {
		return new LongValue(this.value);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.write(source, 8);
	}
}

package lsieun.flink.types;

import java.io.IOException;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.core.memory.MemorySegment;

/**
 * Boxed serializable and comparable boolean type, representing the primitive
 * type {@code boolean}.
 */
@Public
public class BooleanValue implements NormalizableKey<BooleanValue>, ResettableValue<BooleanValue>, CopyableValue<BooleanValue> {
	
	private static final long serialVersionUID = 1L;
	
	public static final BooleanValue TRUE = new BooleanValue(true);
	
	public static final BooleanValue FALSE = new BooleanValue(false);

	private boolean value;

	
	public BooleanValue() {}
	
	public BooleanValue(boolean value) {
		this.value = value;
	}

	public boolean get() {
		return value;
	}

	public void set(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public void setValue(BooleanValue value) {
		this.value = value.value;
	}

	// --------------------------------------------------------------------------------------------

	@Override
	public void write(DataOutputView out) throws IOException {
		out.writeBoolean(this.value);
	}

	@Override
	public void read(DataInputView in) throws IOException {
		this.value = in.readBoolean();
	}
	
	@Override
	public int hashCode() {
		return this.value ? 1 : 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BooleanValue) {
			return ((BooleanValue) obj).value == this.value;
		}
		return false;
	}

	@Override
	public int compareTo(BooleanValue o) {
		final int ov = o.value ? 1 : 0;
		final int tv = this.value ? 1 : 0;
		return tv - ov;
	}
	
	@Override
	public String toString() {
		return this.value ? "true" : "false";
	}

	@Override
	public int getBinaryLength() {
		return 1;
	}

	@Override
	public void copyTo(BooleanValue target) {
		target.value = this.value;
	}

	@Override
	public BooleanValue copy() {
		return new BooleanValue(this.value);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.write(source, 1);
	}

	@Override
	public int getMaxNormalizedKeyLen() {
		return 1;
	}

	@Override
	public void copyNormalizedKey(MemorySegment target, int offset, int len) {
		if (len > 0) {
			target.put(offset, (byte) (this.value ? 1 : 0));
			
			for (offset = offset + 1; len > 1; len--) {
				target.put(offset++, (byte) 0);
			}
		}
	}
}

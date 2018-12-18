package lsieun.flink.types;

import java.io.IOException;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;
import lsieun.flink.core.memory.MemorySegment;

/**
 * Null base type for programs that implements the Key interface.
 */
@Public
public final class NullValue implements NormalizableKey<NullValue>, CopyableValue<NullValue> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The singleton NullValue instance.
	 */
	private final static NullValue INSTANCE = new NullValue();

	/**
	 * Returns the NullValue singleton instance.
	 *  
	 * @return The NullValue singleton instance.
	 */
	public static NullValue getInstance() {
		return INSTANCE;
	}

	// --------------------------------------------------------------------------------------------
	
	/**
	 * Creates a NullValue object.
	 */
	public NullValue() {}
	
	// --------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		return "(null)";
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public void read(DataInputView in) throws IOException {
		in.readBoolean();
	}

	@Override
	public void write(DataOutputView out) throws IOException {
		out.writeBoolean(false);
	}
	
	// --------------------------------------------------------------------------------------------

	@Override
	public int compareTo(NullValue o) {
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		return (o != null && o.getClass() == NullValue.class);
	}

	@Override
	public int hashCode() {
		return 53;
	}
	
	// --------------------------------------------------------------------------------------------

	@Override
	public int getMaxNormalizedKeyLen() {
		return 0;
	}

	@Override
	public void copyNormalizedKey(MemorySegment target, int offset, int len) {
		for (int i = offset; i < offset + len; i++) {
			target.put(i, (byte) 0);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	
	@Override
	public int getBinaryLength() {
		return 1;
	}

	@Override
	public void copyTo(NullValue target) {
	}

	@Override
	public NullValue copy() {
		return NullValue.getInstance();
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		source.readBoolean();
		target.writeBoolean(false);
	}
}

package lsieun.flink.types;

import java.io.IOException;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * Boxed serializable and comparable double precision floating point type, representing the primitive
 * type {@code double}.
 */
@Public
public class DoubleValue implements Comparable<DoubleValue>, ResettableValue<DoubleValue>, CopyableValue<DoubleValue>, Key<DoubleValue> {
	private static final long serialVersionUID = 1L;

	private double value;

	/**
	 * Initializes the encapsulated double with 0.0.
	 */
	public DoubleValue() {
		this.value = 0;
	}

	/**
	 * Initializes the encapsulated double with the provided value.
	 * 
	 * @param value
	 *        Initial value of the encapsulated double.
	 */
	public DoubleValue(double value) {
		this.value = value;
	}

	/**
	 * Returns the value of the encapsulated primitive double.
	 * 
	 * @return the value of the encapsulated primitive double.
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Sets the value of the encapsulated primitive double.
	 * 
	 * @param value
	 *        the new value of the encapsulated primitive double.
	 */
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public void setValue(DoubleValue value) {
		this.value = value.value;
	}

	// --------------------------------------------------------------------------------------------
	
	@Override
	public void read(DataInputView in) throws IOException {
		this.value = in.readDouble();
	}

	@Override
	public void write(DataOutputView out) throws IOException {
		out.writeDouble(this.value);
	}
	
	// --------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
	
	@Override
	public int compareTo(DoubleValue o) {
		final double other = o.value;
		return this.value < other ? -1 : this.value > other ? 1 : 0;
	}

	@Override
	public int hashCode() {
		long temp = Double.doubleToLongBits(this.value);
		return 31 + (int) (temp ^ temp >>> 32);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof DoubleValue) {
			final DoubleValue other = (DoubleValue) obj;
			return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(other.value);
		}
		return false;
	}

	// --------------------------------------------------------------------------------------------
	
	@Override
	public int getBinaryLength() {
		return 8;
	}

	@Override
	public void copyTo(DoubleValue target) {
		target.value = this.value;
	}

	@Override
	public DoubleValue copy() {
		return new DoubleValue(this.value);
	}

	@Override
	public void copy(DataInputView source, DataOutputView target) throws IOException {
		target.write(source, 8);
	}
}

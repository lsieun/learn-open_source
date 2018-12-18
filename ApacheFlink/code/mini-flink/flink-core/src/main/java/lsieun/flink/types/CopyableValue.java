package lsieun.flink.types;

import java.io.IOException;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * Interface to be implemented by basic types that support to be copied efficiently.
 */
@Public
public interface CopyableValue<T> extends Value {
	
	/**
	 * Gets the length of the data type when it is serialized, in bytes.
	 * 
	 * @return The length of the data type, or {@code -1}, if variable length.
	 */
	int getBinaryLength();
	
	/**
	 * Performs a deep copy of this object into the {@code target} instance.
	 *
	 * @param target Object to copy into.
	 */
	void copyTo(T target);

	/**
	 * Performs a deep copy of this object into a new instance.
	 *
	 * This method is useful for generic user-defined functions to clone a
	 * {@link CopyableValue} when storing multiple objects. With object reuse
	 * a deep copy must be created and type erasure prevents calling new.
	 *
	 * @return New object with copied fields.
	 */
	T copy();

	/**
	 * Copies the next serialized instance from {@code source} to {@code target}.
	 *
	 * This method is equivalent to calling {@code IOReadableWritable.read(DataInputView)}
	 * followed by {@code IOReadableWritable.write(DataOutputView)} but does not require
	 * intermediate deserialization.
	 *
	 * @param source Data source for serialized instance.
	 * @param target Data target for serialized instance.
	 *
	 * @see lsieun.flink.core.io.IOReadableWritable
	 */
	void copy(DataInputView source, DataOutputView target) throws IOException;
}

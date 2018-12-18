package lsieun.flink.types;

import lsieun.flink.annotation.Public;

@Public
public interface ResettableValue<T extends Value> extends Value {

	/**
	 * Sets the encapsulated value to another value 
	 *
	 * @param value the new value of the encapsulated value
	 */
	void setValue(T value);
}

package lsieun.flink.types;

import lsieun.flink.annotation.PublicEvolving;

/**
 * This interface has to be implemented by all data types that act as key. Keys are used to establish
 * relationships between values. A key must always be {@link java.lang.Comparable} to other keys of
 * the same type. In addition, keys must implement a correct {@link java.lang.Object#hashCode()} method
 * and {@link java.lang.Object#equals(Object)} method to ensure that grouping on keys works properly.
 * <p>
 * This interface extends {@link lsieun.flink.types.Value} and requires to implement
 * the serialization of its value.
 *
 * @see lsieun.flink.types.Value
 * @see lsieun.flink.core.io.IOReadableWritable
 * @see java.lang.Comparable
 *
 * @deprecated The Key type is a relict of a deprecated and removed API and will be removed
 *             in future (2.0) versions as well.
 */
@Deprecated
@PublicEvolving
public interface Key<T> extends Value, Comparable<T> {

	/**
	 * All keys must override the hash-code function to generate proper deterministic hash codes,
	 * based on their contents.
	 *
	 * @return The hash code of the key
	 */
	public int hashCode();

	/**
	 * Compares the object on equality with another object.
	 *
	 * @param other The other object to compare against.
	 *
	 * @return True, iff this object is identical to the other object, false otherwise.
	 */
	public boolean equals(Object other);
}
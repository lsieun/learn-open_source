package lsieun.flink.types;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.memory.MemorySegment;


/**
 * The base interface for normalizable keys. Normalizable keys can create a binary representation
 * of themselves that is byte-wise comparable. The byte-wise comparison of two normalized keys 
 * proceeds until all bytes are compared or two bytes at the corresponding positions are not equal.
 * If two corresponding byte values are not equal, the lower byte value indicates the lower key.
 * If both normalized keys are byte-wise identical, the actual key may have to be looked at to
 * determine which one is actually lower.
 * <p>
 * The latter depends on whether the normalized key covers the entire key or is just a prefix of the
 * key. A normalized key is considered a prefix, if its length is less than the maximal normalized
 * key length.
 */
@Public
public interface NormalizableKey<T> extends Comparable<T>, Key<T> {

	/**
	 * Gets the maximal length of normalized keys that the data type would produce to determine
	 * the order of instances solely by the normalized key. A value of {@link java.lang.Integer}.MAX_VALUE
	 * is interpreted as infinite.
	 * <p>
	 * For example, 32 bit integers return four, while Strings (potentially unlimited in length) return
	 * {@link java.lang.Integer}.MAX_VALUE.
	 * 
	 * @return The maximal length of normalized keys.
	 */
	int getMaxNormalizedKeyLen();
	
	/**
	 * Writes a normalized key for the given record into the target byte array, starting at the specified position
	 * an writing exactly the given number of bytes. Note that the comparison of the bytes is treating the bytes
	 * as unsigned bytes: {@code int byteI = bytes[i] & 0xFF;}
	 * <p>
	 * If the meaningful part of the normalized key takes less than the given number of bytes, than it must be padded.
	 * Padding is typically required for variable length data types, such as strings. The padding uses a special
	 * character, either {@code 0} or {@code 0xff}, depending on whether shorter values are sorted to the beginning or
	 * the end. 
	 * 
	 * @param memory The memory segment to put the normalized key bytes into.
	 * @param offset The offset in the byte array where the normalized key's bytes should start.
	 * @param len The number of bytes to put.
	 */
	void copyNormalizedKey(MemorySegment memory, int offset, int len);
}

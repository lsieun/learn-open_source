package lsieun.flink.types;


import java.io.Serializable;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.io.IOReadableWritable;

/**
 * Basic value interface for types that act as serializable values.
 * <p>
 * This interface extends {@link IOReadableWritable} and requires to implement
 * the serialization of its value.
 *
 * @see lsieun.flink.core.io.IOReadableWritable
 */
@Public
public interface Value extends IOReadableWritable, Serializable {
}

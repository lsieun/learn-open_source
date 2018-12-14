package lsieun.flink.api.common.io;

import lsieun.flink.annotation.Public;

/**
 * Base implementation for input formats that split the input at a delimiter into records.
 * The parsing of the record bytes into the record has to be implemented in the
 * {@link #readRecord(Object, byte[], int, int)} method.
 *
 * <p>The default delimiter is the newline character {@code '\n'}.</p>
 */
@Public
public abstract class DelimitedInputFormat<OT> extends FileInputFormat<OT> {
}

package lsieun.flink.core.io;

import java.io.Serializable;

import lsieun.flink.annotation.Public;

/**
 * This interface must be implemented by all kind of input splits that can be assigned to input formats.
 *
 * <p>Input splits are transferred in serialized form via the messages, so they need to be serializable
 * as defined by {@link java.io.Serializable}.</p>
 */
@Public
public interface InputSplit extends Serializable {

    /**
     * Returns the number of this input split.
     *
     * @return the number of this input split
     */
    int getSplitNumber();
}

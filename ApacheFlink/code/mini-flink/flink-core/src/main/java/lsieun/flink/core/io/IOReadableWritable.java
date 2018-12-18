package lsieun.flink.core.io;


import java.io.IOException;

import lsieun.flink.annotation.Public;
import lsieun.flink.core.memory.DataInputView;
import lsieun.flink.core.memory.DataOutputView;

/**
 * This interface must be implemented by every class whose objects have to be serialized to their binary representation
 * and vice-versa. In particular, records have to implement this interface in order to specify how their data can be
 * transferred to a binary representation.
 *
 * <p>When implementing this Interface make sure that the implementing class has a default
 * (zero-argument) constructor!
 */
@Public
public interface IOReadableWritable {

    /**
     * Writes the object's internal data to the given data output view.
     *
     * @param out
     *        the output view to receive the data.
     * @throws IOException
     *         thrown if any error occurs while writing to the output stream
     */
    void write(DataOutputView out) throws IOException;

    /**
     * Reads the object's internal data from the given data input view.
     *
     * @param in
     *        the input view to read the data from
     * @throws IOException
     *         thrown if any error occurs while reading from the input stream
     */
    void read(DataInputView in) throws IOException;

}

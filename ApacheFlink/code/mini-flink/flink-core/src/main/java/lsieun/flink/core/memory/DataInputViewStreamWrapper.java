package lsieun.flink.core.memory;


import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import lsieun.flink.annotation.PublicEvolving;

/**
 * Utility class that turns an {@link InputStream} into a {@link DataInputView}.
 */
@PublicEvolving
public class DataInputViewStreamWrapper extends DataInputStream implements DataInputView {

    public DataInputViewStreamWrapper(InputStream in) {
        super(in);
    }

    @Override
    public void skipBytesToRead(int numBytes) throws IOException {
        if (skipBytes(numBytes) != numBytes){
            throw new EOFException("Could not skip " + numBytes + " bytes.");
        }
    }
}

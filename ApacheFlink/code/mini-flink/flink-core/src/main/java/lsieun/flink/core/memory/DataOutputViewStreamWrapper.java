package lsieun.flink.core.memory;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lsieun.flink.annotation.PublicEvolving;

/**
 * Utility class that turns an {@link OutputStream} into a {@link DataOutputView}.
 */
@PublicEvolving
public class DataOutputViewStreamWrapper extends DataOutputStream implements DataOutputView {

    private byte[] tempBuffer;

    public DataOutputViewStreamWrapper(OutputStream out) {
        super(out);
    }

    @Override
    public void skipBytesToWrite(int numBytes) throws IOException {
        if (tempBuffer == null) {
            tempBuffer = new byte[4096];
        }

        while (numBytes > 0) {
            int toWrite = Math.min(numBytes, tempBuffer.length);
            write(tempBuffer, 0, toWrite);
            numBytes -= toWrite;
        }
    }

    @Override
    public void write(DataInputView source, int numBytes) throws IOException {
        if (tempBuffer == null) {
            tempBuffer = new byte[4096];
        }

        while (numBytes > 0) {
            int toCopy = Math.min(numBytes, tempBuffer.length);
            source.readFully(tempBuffer, 0, toCopy);
            write(tempBuffer, 0, toCopy);
            numBytes -= toCopy;
        }
    }
}

package lsieun.flink.api.java.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.api.common.io.DelimitedInputFormat;
import lsieun.flink.configuration.Configuration;
import lsieun.flink.core.fs.Path;

/**
 * Input Format that reads text files. Each line results in another element.
 */
@PublicEvolving
public class TextInputFormat extends DelimitedInputFormat<String> {

    private static final long serialVersionUID = 1L;

    // -------------------------------------- Constants -------------------------------------------

    /**
     * Code of \r, used to remove \r from a line when the line ends with \r\n.
     */
    private static final byte CARRIAGE_RETURN = (byte) '\r';

    /**
     * Code of \n, used to identify if \n is used as delimiter.
     */
    private static final byte NEW_LINE = (byte) '\n';

    // -------------------------------------- Fields -------------------------------------------

    /**
     * The name of the charset to use for decoding.
     */
    private String charsetName = "UTF-8";

    // -------------------------------------- Constructors -------------------------------------------

    public TextInputFormat(Path filePath) {
        super(filePath, null);
    }

    // -------------------------------------- Getters & Setters -------------------------------------------

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        if (charsetName == null) {
            throw new IllegalArgumentException("Charset must not be null.");
        }

        this.charsetName = charsetName;
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public void configure(Configuration parameters) {
        super.configure(parameters);

        if (charsetName == null || !Charset.isSupported(charsetName)) {
            throw new RuntimeException("Unsupported charset: " + charsetName);
        }
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public String readRecord(String reusable, byte[] bytes, int offset, int numBytes) throws IOException {
        //Check if \n is used as delimiter and the end of this line is a \r, then remove \r from the line
        if (this.getDelimiter() != null && this.getDelimiter().length == 1
                && this.getDelimiter()[0] == NEW_LINE && offset + numBytes >= 1
                && bytes[offset + numBytes - 1] == CARRIAGE_RETURN){
            numBytes -= 1;
        }

        return new String(bytes, offset, numBytes, this.charsetName);
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "TextInputFormat (" + Arrays.toString(getFilePaths()) + ") - " + this.charsetName;
    }

    @Override
    public boolean supportsMultiPaths() {
        return true;
    }
}

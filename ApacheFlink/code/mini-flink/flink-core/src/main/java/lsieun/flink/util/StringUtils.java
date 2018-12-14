package lsieun.flink.util;

import lsieun.flink.annotation.PublicEvolving;


/**
 * Utility class to convert objects into strings in vice-versa.
 */
@PublicEvolving
public class StringUtils {
    private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Given an array of bytes it will convert the bytes to a hex string
     * representation of the bytes.
     *
     * @param bytes
     *        the bytes to convert in a hex string
     * @param start
     *        start index, inclusively
     * @param end
     *        end index, exclusively
     * @return hex string representation of the byte array
     */
    public static String byteToHexString(final byte[] bytes, final int start, final int end) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes == null");
        }

        int length = end - start;
        char[] out = new char[length * 2];

        for (int i = start, j = 0; i < end; i++) {
            out[j++] = HEX_CHARS[(0xF0 & bytes[i]) >>> 4];
            out[j++] = HEX_CHARS[0x0F & bytes[i]];
        }

        return new String(out);
    }

    /**
     * Given an array of bytes it will convert the bytes to a hex string
     * representation of the bytes.
     *
     * @param bytes
     *        the bytes to convert in a hex string
     * @return hex string representation of the byte array
     */
    public static String byteToHexString(final byte[] bytes) {
        return byteToHexString(bytes, 0, bytes.length);
    }

    /**
     * Given a hex string this will return the byte array corresponding to the
     * string .
     *
     * @param hex
     *        the hex String array
     * @return a byte array that is a hex string representation of the given
     *         string. The size of the byte array is therefore hex.length/2
     */
    public static byte[] hexStringToByte(final String hex) {
        final byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }

    // ------------------------------------------------------------------------

    /** Prevent instantiation of this utility class. */
    private StringUtils() {}
}

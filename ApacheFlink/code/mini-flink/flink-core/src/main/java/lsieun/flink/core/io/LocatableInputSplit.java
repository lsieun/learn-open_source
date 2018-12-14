package lsieun.flink.core.io;

import java.io.Serializable;
import java.util.Arrays;

import lsieun.flink.annotation.Public;

/**
 * A locatable input split is an input split referring to input data which is located on one or more hosts.
 */
@Public
public class LocatableInputSplit implements InputSplit, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String[] EMPTY_ARR = new String[0];

    /** The number of the split. */
    private final int splitNumber;

    /** The names of the hosts storing the data this input split refers to. */
    private final String[] hostnames;

    // --------------------------------------------------------------------------------------------

    /**
     * Creates a new locatable input split that refers to a multiple host as its data location.
     *
     * @param splitNumber The number of the split
     * @param hostnames The names of the hosts storing the data this input split refers to.
     */
    public LocatableInputSplit(int splitNumber, String[] hostnames) {
        this.splitNumber = splitNumber;
        this.hostnames = hostnames == null ? EMPTY_ARR : hostnames;
    }

    /**
     * Creates a new locatable input split that refers to a single host as its data location.
     *
     * @param splitNumber The number of the split.
     * @param hostname The names of the host storing the data this input split refers to.
     */
    public LocatableInputSplit(int splitNumber, String hostname) {
        this.splitNumber = splitNumber;
        this.hostnames = hostname == null ? EMPTY_ARR : new String[] { hostname };
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public int getSplitNumber() {
        return this.splitNumber;
    }

    /**
     * Returns the names of the hosts storing the data this input split refers to
     *
     * @return the names of the hosts storing the data this input split refers to
     */
    public String[] getHostnames() {
        return this.hostnames;
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        return this.splitNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof LocatableInputSplit) {
            LocatableInputSplit other = (LocatableInputSplit) obj;
            return other.splitNumber == this.splitNumber && Arrays.deepEquals(other.hostnames, this.hostnames);
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Locatable Split (" + splitNumber + ") at " + Arrays.toString(this.hostnames);
    }
}

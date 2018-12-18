package lsieun.flink.core.fs.local;


import java.io.IOException;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.fs.BlockLocation;

/**
 * Implementation of the {@link BlockLocation} interface for a local file system.
 */
@Internal
public class LocalBlockLocation implements BlockLocation {

    private final long length;

    private final String[] hosts;

    public LocalBlockLocation(final String host, final long length) {
        this.hosts = new String[] { host };
        this.length = length;
    }

    @Override
    public String[] getHosts() throws IOException {
        return this.hosts;
    }

    @Override
    public long getLength() {
        return this.length;
    }

    @Override
    public long getOffset() {
        return 0;
    }

    @Override
    public int compareTo(final BlockLocation o) {
        return 0;
    }
}

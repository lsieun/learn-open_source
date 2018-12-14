package lsieun.flink.api.common.io;

import java.io.IOException;

import lsieun.flink.annotation.Public;
import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.api.common.functions.RuntimeContext;
import lsieun.flink.core.io.InputSplit;

/**
 * An abstract stub implementation for Rich input formats.
 * Rich formats have access to their runtime execution context via {@link #getRuntimeContext()}.
 */
@Public
public abstract class RichInputFormat<OT, T extends InputSplit> implements InputFormat<OT, T> {

    private static final long serialVersionUID = 1L;

    // --------------------------------------------------------------------------------------------
    //  Runtime context access
    // --------------------------------------------------------------------------------------------

    private transient RuntimeContext runtimeContext;

    public void setRuntimeContext(RuntimeContext t) {
        this.runtimeContext = t;
    }

    public RuntimeContext getRuntimeContext() {
        if (this.runtimeContext != null) {
            return this.runtimeContext;
        } else {
            throw new IllegalStateException("The runtime context has not been initialized yet. Try accessing " +
                    "it in one of the other life cycle methods.");
        }
    }

    /**
     * Opens this InputFormat instance. This method is called once per parallel instance.
     * Resources should be allocated in this method. (e.g. database connections, cache, etc.)
     *
     * @see InputFormat
     * @throws IOException in case allocating the resources failed.
     */
    @PublicEvolving
    public void openInputFormat() throws IOException {
        //do nothing here, just for subclasses
    }

    /**
     * Closes this InputFormat instance. This method is called once per parallel instance.
     * Resources allocated during {@link #openInputFormat()} should be closed in this method.
     *
     * @see InputFormat
     * @throws IOException in case closing the resources failed
     */
    @PublicEvolving
    public void closeInputFormat() throws IOException {
        //do nothing here, just for subclasses
    }
}

package lsieun.flink.api.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import lsieun.flink.annotation.Public;

/**
 * A config to define the behavior of the program execution. It allows to define (among other
 * options) the following settings:
 */
@Public
public class ExecutionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The flag value indicating use of the default parallelism. This value can
     * be used to reset the parallelism back to the default state.
     */
    public static final int PARALLELISM_DEFAULT = -1;

    /**
     * The flag value indicating an unknown or unset parallelism. This value is
     * not a valid parallelism and indicates that the parallelism should remain
     * unchanged.
     */
    public static final int PARALLELISM_UNKNOWN = -2;

    private static final long DEFAULT_RESTART_DELAY = 10000L;

    // --------------------------------------------------------------------------------------------

    /** Defines how data exchange happens - batch or pipelined */
    private ExecutionMode executionMode = ExecutionMode.PIPELINED;

    private boolean useClosureCleaner = true;

    private int parallelism = PARALLELISM_DEFAULT;

    /**
     * The program wide maximum parallelism used for operators which haven't specified a maximum
     * parallelism. The maximum parallelism specifies the upper limit for dynamic scaling and the
     * number of key groups used for partitioned state.
     */
    private int maxParallelism = -1;

    public ExecutionConfig setParallelism(int parallelism) {
        this.parallelism = parallelism;
        return this;
    }

    /**
     * Sets the execution mode to execute the program. The execution mode defines whether
     * data exchanges are performed in a batch or on a pipelined manner.
     *
     * The default execution mode is {@link ExecutionMode#PIPELINED}.
     *
     * @param executionMode The execution mode to use.
     */
    public void setExecutionMode(ExecutionMode executionMode) {
        this.executionMode = executionMode;
    }

    /**
     * Gets the execution mode used to execute the program. The execution mode defines whether
     * data exchanges are performed in a batch or on a pipelined manner.
     *
     * The default execution mode is {@link ExecutionMode#PIPELINED}.
     *
     * @return The execution mode for the program.
     */
    public ExecutionMode getExecutionMode() {
        return executionMode;
    }


    /**
     * Abstract class for a custom user configuration object registered at the execution config.
     *
     * This user config is accessible at runtime through
     * getRuntimeContext().getExecutionConfig().GlobalJobParameters()
     */
    public static class GlobalJobParameters implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * Convert UserConfig into a {@code Map<String, String>} representation.
         * This can be used by the runtime, for example for presenting the user config in the web frontend.
         *
         * @return Key/Value representation of the UserConfig
         */
        public Map<String, String> toMap() {
            return Collections.emptyMap();
        }
    }
}

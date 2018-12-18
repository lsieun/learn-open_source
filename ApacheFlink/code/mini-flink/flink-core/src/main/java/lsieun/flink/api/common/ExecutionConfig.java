package lsieun.flink.api.common;

import static lsieun.flink.util.Preconditions.checkArgument;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import lsieun.flink.annotation.Public;
import lsieun.flink.annotation.PublicEvolving;

/**
 * A config to define the behavior of the program execution. It allows to define (among other
 * options) the following settings:
 */
@Public
public class ExecutionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    // -------------------------------------- Constants -------------------------------------------

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

    // -------------------------------------- Fields -------------------------------------------

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

    // -------------------------------------- Getters & Setters -------------------------------------------

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
     * Enables the ClosureCleaner. This analyzes user code functions and sets fields to null
     * that are not used. This will in most cases make closures or anonymous inner classes
     * serializable that where not serializable due to some Scala or Java implementation artifact.
     * User code must be serializable because it needs to be sent to worker nodes.
     */
    public ExecutionConfig enableClosureCleaner() {
        useClosureCleaner = true;
        return this;
    }

    /**
     * Disables the ClosureCleaner.
     *
     * @see #enableClosureCleaner()
     */
    public ExecutionConfig disableClosureCleaner() {
        useClosureCleaner = false;
        return this;
    }

    /**
     * Returns whether the ClosureCleaner is enabled.
     *
     * @see #enableClosureCleaner()
     */
    public boolean isClosureCleanerEnabled() {
        return useClosureCleaner;
    }

    /**
     * Gets the parallelism with which operation are executed by default. Operations can
     * individually override this value to use a specific parallelism.
     *
     * Other operations may need to run with a different parallelism - for example calling
     * a reduce operation over the entire data set will involve an operation that runs
     * with a parallelism of one (the final reduce to the single result value).
     *
     * @return The parallelism used by operations, unless they override that value. This method
     *         returns {@link #PARALLELISM_DEFAULT} if the environment's default parallelism
     *         should be used.
     */
    public int getParallelism() {
        return parallelism;
    }

    /**
     * Sets the parallelism for operations executed through this environment.
     * Setting a parallelism of x here will cause all operators (such as join, map, reduce) to run with
     * x parallel instances.
     * <p>
     * This method overrides the default parallelism for this environment.
     * The local execution environment uses by default a value equal to the number of hardware
     * contexts (CPU cores / threads). When executing the program via the command line client
     * from a JAR file, the default parallelism is the one configured for that setup.
     *
     * @param parallelism The parallelism to use
     */
    public ExecutionConfig setParallelism(int parallelism) {
        if (parallelism != PARALLELISM_UNKNOWN) {
            if (parallelism < 1 && parallelism != PARALLELISM_DEFAULT) {
                throw new IllegalArgumentException(
                        "Parallelism must be at least one, or ExecutionConfig.PARALLELISM_DEFAULT (use system default).");
            }
            this.parallelism = parallelism;
        }
        return this;
    }

    /**
     * Gets the maximum degree of parallelism defined for the program.
     *
     * The maximum degree of parallelism specifies the upper limit for dynamic scaling. It also
     * defines the number of key groups used for partitioned state.
     *
     * @return Maximum degree of parallelism
     */
    @PublicEvolving
    public int getMaxParallelism() {
        return maxParallelism;
    }

    /**
     * Sets the maximum degree of parallelism defined for the program.
     *
     * The maximum degree of parallelism specifies the upper limit for dynamic scaling. It also
     * defines the number of key groups used for partitioned state.
     *
     * @param maxParallelism Maximum degree of parallelism to be used for the program.
     */
    @PublicEvolving
    public void setMaxParallelism(int maxParallelism) {
        checkArgument(maxParallelism > 0, "The maximum parallelism must be greater than 0.");
        this.maxParallelism = maxParallelism;
    }




    // -------------------------------------- Inner Class -------------------------------------------

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

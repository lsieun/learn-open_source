package lsieun.flink.api.common;

import lsieun.flink.annotation.Public;

/**
 * The execution mode specifies how a batch program is executed in terms
 * of data exchange: pipelining or batched.
 */
@Public
public enum ExecutionMode {

    /**
     * Executes the program in a pipelined fashion (including shuffles and broadcasts),
     * except for data exchanges that are susceptible to deadlocks when pipelining.
     * These data exchanges are performed in a batch manner.
     *
     * An example of situations that are susceptible to deadlocks (when executed in a
     * pipelined manner) are data flows that branch (one data set consumed by multiple
     * operations) and re-join later:
     * <pre>{@code
     * DataSet data = ...;
     * DataSet mapped1 = data.map(new MyMapper());
     * DataSet mapped2 = data.map(new AnotherMapper());
     * mapped1.join(mapped2).where(...).equalTo(...);
     * }</pre>
     */
    PIPELINED,

    /**
     * Executes the program in a pipelined fashion (including shuffles and broadcasts),
     * <strong>including</strong> data exchanges that are susceptible to deadlocks when
     * executed via pipelining.
     *
     * Usually, {@link #PIPELINED} is the preferable option, which pipelines most
     * data exchanges and only uses batch data exchanges in situations that are
     * susceptible to deadlocks.
     *
     * This option should only be used with care and only in situations where the
     * programmer is sure that the program is safe for full pipelining and that
     * Flink was too conservative when choosing the batch exchange at a certain
     * point.
     */
    PIPELINED_FORCED,

//	This is for later, we are missing a bit of infrastructure for this.
//	/**
//	 * The execution mode starts executing the program in a pipelined fashion
//	 * (except for deadlock prone situations), similar to the {@link #PIPELINED}
//	 * option. In the case of a task failure, re-execution happens in a batched
//	 * mode, as defined for the {@link #BATCH} option.
//	 */
//	PIPELINED_WITH_BATCH_FALLBACK,

    /**
     * This mode executes all shuffles and broadcasts in a batch fashion, while
     * pipelining data between operations that exchange data only locally
     * between one producer and one consumer.
     */
    BATCH,

    /**
     * This mode executes the program in a strict batch way, including all points
     * where data is forwarded locally from one producer to one consumer. This mode
     * is typically more expensive to execute than the {@link #BATCH} mode. It does
     * guarantee that no successive operations are ever executed concurrently.
     */
    BATCH_FORCED
}

package lsieun.flink.core.io;

import java.io.Serializable;

import lsieun.flink.annotation.Public;

/**
 * InputSplitSources create {@link InputSplit}s that define portions of data to be produced
 * by {@link lsieun.flink.api.common.io.InputFormat}s.
 *
 * @param <T> The type of the input splits created by the source.
 */
@Public
public interface InputSplitSource<T extends InputSplit> extends Serializable {

    /**
     * Computes the input splits. The given minimum number of splits is a hint as to how
     * many splits are desired.
     *
     * @param minNumSplits Number of minimal input splits, as a hint.
     * @return An array of input splits.
     *
     * @throws Exception Exceptions when creating the input splits may be forwarded and will cause the
     *                   execution to permanently fail.
     */
    T[] createInputSplits(int minNumSplits) throws Exception;

    /**
     * Returns the assigner for the input splits. Assigner determines which parallel instance of the
     * input format gets which input split.
     *
     * @return The input split assigner.
     */
    InputSplitAssigner getInputSplitAssigner(T[] inputSplits);
}

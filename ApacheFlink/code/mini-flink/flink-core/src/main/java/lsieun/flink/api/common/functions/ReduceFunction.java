package lsieun.flink.api.common.functions;


import java.io.Serializable;

import lsieun.flink.annotation.Public;

/**
 * Base interface for Reduce functions. Reduce functions combine groups of elements to
 * a single value, by taking always two elements and combining them into one. Reduce functions
 * may be used on entire data sets, or on grouped data sets. In the latter case, each group is reduced
 * individually.
 *
 * <p>For a reduce functions that work on an entire group at the same time (such as the
 * MapReduce/Hadoop-style reduce), see {@link GroupReduceFunction}. In the general case,
 * ReduceFunctions are considered faster, because they allow the system to use more efficient
 * execution strategies.
 *
 * <p>The basic syntax for using a grouped ReduceFunction is as follows:
 * <pre>{@code
 * DataSet<X> input = ...;
 *
 * DataSet<X> result = input.groupBy(<key-definition>).reduce(new MyReduceFunction());
 * }</pre>
 *
 * <p>Like all functions, the ReduceFunction needs to be serializable, as defined in {@link java.io.Serializable}.
 *
 * @param <T> Type of the elements that this function processes.
 */
@Public
@FunctionalInterface
public interface ReduceFunction<T> extends Function, Serializable {

    /**
     * The core method of ReduceFunction, combining two values into one value of the same type.
     * The reduce function is consecutively applied to all values of a group until only a single value remains.
     *
     * @param value1 The first value to combine.
     * @param value2 The second value to combine.
     * @return The combined value of both input values.
     *
     * @throws Exception This method may throw exceptions. Throwing an exception will cause the operation
     *                   to fail and may trigger recovery.
     */
    T reduce(T value1, T value2) throws Exception;
}

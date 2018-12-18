package lsieun.flink.api.common.functions;


import java.io.Serializable;

import lsieun.flink.annotation.Public;

/**
 * Base interface for Fold functions. Fold functions combine groups of elements to
 * a single value, by applying a binary operation to an initial accumulator element every element from a group elements.
 *
 * <p>The basic syntax for using a FoldFunction is as follows:
 * <pre>{@code
 * DataSet<X> input = ...;
 *
 * X initialValue = ...;
 * DataSet<X> result = input.fold(new MyFoldFunction(), initialValue);
 * }</pre>
 *
 * <p>Like all functions, the FoldFunction needs to be serializable, as defined in {@link java.io.Serializable}.
 *
 * @param <T> Type of the initial input and the returned element
 * @param <O> Type of the elements that the group/list/stream contains
 *
 * @deprecated use {@link AggregateFunction} instead
 */
@Public
@Deprecated
@FunctionalInterface
public interface FoldFunction<O, T> extends Function, Serializable {

    /**
     * The core method of FoldFunction, combining two values into one value of the same type.
     * The fold function is consecutively applied to all values of a group until only a single value remains.
     *
     * @param accumulator The initial value, and accumulator.
     * @param value The value from the group to "fold" into the accumulator.
     * @return The accumulator that is at the end of the "folding" the group.
     *
     * @throws Exception This method may throw exceptions. Throwing an exception will cause the operation
     *                   to fail and may trigger recovery.
     */
    T fold(T accumulator, O value) throws Exception;
}

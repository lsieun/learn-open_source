package lsieun.flink.api.common.functions;


import java.io.Serializable;

import lsieun.flink.annotation.Public;
import lsieun.flink.util.Collector;

/**
 * Base interface for flatMap functions. FlatMap functions take elements and transform them,
 * into zero, one, or more elements. Typical applications can be splitting elements, or unnesting lists
 * and arrays. Operations that produce multiple strictly one result element per input element can also
 * use the {@link MapFunction}.
 *
 * <p>The basic syntax for using a FlatMapFunction is as follows:
 * <pre>{@code
 * DataSet<X> input = ...;
 *
 * DataSet<Y> result = input.flatMap(new MyFlatMapFunction());
 * }</pre>
 *
 * @param <T> Type of the input elements.
 * @param <O> Type of the returned elements.
 */
@Public
@FunctionalInterface
public interface FlatMapFunction<T, O> extends Function, Serializable {

    /**
     * The core method of the FlatMapFunction. Takes an element from the input data set and transforms
     * it into zero, one, or more elements.
     *
     * @param value The input value.
     * @param out The collector for returning result values.
     *
     * @throws Exception This method may throw exceptions. Throwing an exception will cause the operation
     *                   to fail and may trigger recovery.
     */
    void flatMap(T value, Collector<O> out) throws Exception;
}

package lsieun.flink.api.common.functions;


import java.io.Serializable;

import lsieun.flink.annotation.Public;
import lsieun.flink.util.Collector;

/**
 * Interface for "mapPartition" functions. A "mapPartition" function is called a single time per
 * data partition receives an Iterable with data elements of that partition. It may return an
 * arbitrary number of data elements.
 *
 * <p>This function is intended to provide enhanced flexibility in the processing of elements in a partition.
 * For most of the simple use cases, consider using the {@link MapFunction} or {@link FlatMapFunction}.
 *
 * <p>The basic syntax for a MapPartitionFunction is as follows:
 * <pre>{@code
 * DataSet<X> input = ...;
 *
 * DataSet<Y> result = input.mapPartition(new MyMapPartitionFunction());
 * }</pre>
 *
 * @param <T> Type of the input elements.
 * @param <O> Type of the returned elements.
 */
@Public
@FunctionalInterface
public interface MapPartitionFunction<T, O> extends Function, Serializable {

    /**
     * A user-implemented function that modifies or transforms an incoming object.
     *
     * @param values All records for the mapper
     * @param out The collector to hand results to.
     * @throws Exception This method may throw exceptions. Throwing an exception will cause the operation
     *                   to fail and may trigger recovery.
     */
    void mapPartition(Iterable<T> values, Collector<O> out) throws Exception;
}

package lsieun.flink.api.common.functions;


import java.io.Serializable;

import lsieun.flink.annotation.Public;
import lsieun.flink.util.Collector;

/**
 * Generic interface used for combine functions ("combiners"). Combiners act as auxiliaries to a {@link GroupReduceFunction}
 * and "pre-reduce" the data. The combine functions typically do not see the entire group of elements, but
 * only a sub-group.
 *
 * <p>Combine functions are frequently helpful in increasing the program efficiency, because they allow the system to
 * reduce the data volume earlier, before the entire groups have been collected.
 *
 * <p>This special variant of the combine function supports to return more than one element per group.
 * It is frequently less efficient to use than the {@link CombineFunction}.
 *
 * @param <IN> The data type processed by the combine function.
 * @param <OUT> The data type emitted by the combine function.
 */
@Public
@FunctionalInterface
public interface GroupCombineFunction<IN, OUT> extends Function, Serializable {

    /**
     * The combine method, called (potentially multiple timed) with subgroups of elements.
     *
     * @param values The elements to be combined.
     * @param out The collector to use to return values from the function.
     *
     * @throws Exception The function may throw Exceptions, which will cause the program to cancel,
     *                   and may trigger the recovery logic.
     */
    void combine(Iterable<IN> values, Collector<OUT> out) throws Exception;
}

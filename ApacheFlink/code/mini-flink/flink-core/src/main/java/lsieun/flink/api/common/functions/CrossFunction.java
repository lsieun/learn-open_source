package lsieun.flink.api.common.functions;


import java.io.Serializable;

import lsieun.flink.annotation.Public;

/**
 * Interface for Cross functions. Cross functions are applied to the Cartesian product
 * of their inputs and are called for each pair of elements.
 *
 * <p>They are optional, a means of convenience that can be used to directly manipulate the
 * pair of elements instead of producing 2-tuples containing the pairs.
 *
 * <p>The basic syntax for using Cross on two data sets is as follows:
 * <pre>{@code
 * DataSet<X> set1 = ...;
 * DataSet<Y> set2 = ...;
 *
 * set1.cross(set2).with(new MyCrossFunction());
 * }</pre>
 *
 * <p>{@code set1} is here considered the first input, {@code set2} the second input.
 *
 * @param <IN1> The type of the elements in the first input.
 * @param <IN2> The type of the elements in the second input.
 * @param <OUT> The type of the result elements.
 */
@Public
@FunctionalInterface
public interface CrossFunction<IN1, IN2, OUT> extends Function, Serializable {

    /**
     * Cross UDF method. Called once per pair of elements in the Cartesian product of the inputs.
     *
     * @param val1 Element from first input.
     * @param val2 Element from the second input.
     * @return The result element.
     *
     * @throws Exception The function may throw Exceptions, which will cause the program to cancel,
     *                   and may trigger the recovery logic.
     */
    OUT cross(IN1 val1, IN2 val2) throws Exception;

}

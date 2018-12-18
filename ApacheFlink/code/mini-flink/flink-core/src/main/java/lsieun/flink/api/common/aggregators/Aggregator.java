package lsieun.flink.api.common.aggregators;


import java.io.Serializable;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.types.Value;

/**
 * Aggregators are a means of aggregating values across parallel instances of a function. Aggregators
 * collect simple statistics (such as the number of processed elements) about the actual work performed in a function.
 * Aggregators are specific to iterations and are commonly used to check the convergence of an iteration by using a
 * {@link ConvergenceCriterion}. In contrast to the {@link org.apache.flink.api.common.accumulators.Accumulator} (whose result is available at the end of a job,
 * the aggregators are computed once per iteration superstep. Their value can be used to check for convergence (at the end
 * of the iteration superstep) and it can be accessed in the next iteration superstep.
 * <p>
 * Aggregators must be registered at the iteration inside which they are used via the function. In the Java API, the
 * method is "IterativeDataSet.registerAggregator(...)" or "IterativeDataSet.registerAggregationConvergenceCriterion(...)"
 * when using the aggregator together with a convergence criterion. Aggregators are always registered under a name. That
 * name can be used to access the aggregator at runtime from within a function. The following code snippet shows a typical
 * case. Here, it count across all parallel instances how many elements are filtered out by a function.
 *
 * <pre>
 * // the user-defined function
 * public class MyFilter extends FilterFunction&lt;Double&gt; {
 *     private LongSumAggregator agg;
 *
 *     public void open(Configuration parameters) {
 *         agg = getIterationRuntimeContext().getIterationAggregator("numFiltered");
 *     }
 *
 *     public boolean filter (Double value) {
 *         if (value &gt; 1000000.0) {
 *             agg.aggregate(1);
 *             return false
 *         }
 *
 *         return true;
 *     }
 * }
 *
 * // the iteration where the aggregator is registered
 * IterativeDataSet&lt;Double&gt; iteration = input.iterate(100).registerAggregator("numFiltered", LongSumAggregator.class);
 * ...
 * DataSet&lt;Double&gt; filtered = someIntermediateResult.filter(new MyFilter);
 * ...
 * DataSet&lt;Double&gt; result = iteration.closeWith(filtered);
 * ...
 * </pre>
 *
 * <p>
 * Aggregators must be <i>distributive</i>: An aggregator must be able to pre-aggregate values and it must be able
 * to aggregate these pre-aggregated values to form the final aggregate. Many aggregation functions fulfill this
 * condition (sum, min, max) and others can be brought into that form: One can expressing <i>count</i> as a sum over
 * values of one, and one can express <i>average</i> through a sum and a count.
 *
 * @param <T> The type of the aggregated value.
 */
@PublicEvolving
public interface Aggregator<T extends Value> extends Serializable {

    /**
     * Gets the aggregator's current aggregate.
     *
     * @return The aggregator's current aggregate.
     */
    T getAggregate();

    /**
     * Aggregates the given element. In the case of a <i>sum</i> aggregator, this method adds the given
     * value to the sum.
     *
     * @param element The element to aggregate.
     */
    void aggregate(T element);

    /**
     * Resets the internal state of the aggregator. This must bring the aggregator into the same
     * state as if it was newly initialized.
     */
    void reset();
}

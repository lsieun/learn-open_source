package lsieun.flink.api.common.functions;


import lsieun.flink.annotation.Public;
import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.api.common.aggregators.Aggregator;
import lsieun.flink.types.Value;

/**
 * A specialization of the {@link RuntimeContext} available in iterative computations of the
 * DataSet API.
 */
@Public
public interface IterationRuntimeContext extends RuntimeContext {

    /**
     * Gets the number of the current superstep. Superstep numbers start at <i>1</i>.
     *
     * @return The number of the current superstep.
     */
    int getSuperstepNumber();

    @PublicEvolving
    <T extends Aggregator<?>> T getIterationAggregator(String name);

    <T extends Value> T getPreviousIterationAggregate(String name);
}

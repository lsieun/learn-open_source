package lsieun.flink.api.common.aggregators;


import java.io.Serializable;

import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.types.Value;

/**
 * Used to check for convergence.
 */
@PublicEvolving
public interface ConvergenceCriterion<T extends Value> extends Serializable {

    /**
     * Decide whether the iterative algorithm has converged
     */
    boolean isConverged(int iteration, T value);
}

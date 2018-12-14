package lsieun.flink.api.common.functions;

import lsieun.flink.annotation.Public;

@Public
public interface RuntimeContext {

    /**
     * Returns the name of the task in which the UDF runs, as assigned during plan construction.
     *
     * @return The name of the task in which the UDF runs.
     */
    String getTaskName();

    /**
     * Gets the parallelism with which the parallel task runs.
     *
     * @return The parallelism with which the parallel task runs.
     */
    int getNumberOfParallelSubtasks();
}

package lsieun.flink.api.common.functions;


import lsieun.flink.annotation.Public;

/**
 * Function to implement a custom partition assignment for keys.
 *
 * @param <K> The type of the key to be partitioned.
 */
@Public
@FunctionalInterface
public interface Partitioner<K> extends java.io.Serializable, Function {

    /**
     * Computes the partition for the given key.
     *
     * @param key The key.
     * @param numPartitions The number of partitions to partition into.
     * @return The partition index.
     */
    int partition(K key, int numPartitions);
}

package lsieun.flink.api.common.typeinfo;


import lsieun.flink.annotation.Public;
import lsieun.flink.api.common.ExecutionConfig;
import lsieun.flink.api.common.typeutils.TypeComparator;

/**
 * An atomic type is a type that is treated as one indivisible unit and where the entire type acts
 * as a key. The atomic type defines the method to create a comparator for this type as a key.
 * Example atomic types are the basic types (int, long, String, ...) and comparable custom classes.
 *
 * <p>In contrast to atomic types are composite types, where the type information is aware of the individual
 * fields and individual fields may be used as a key.
 */
@Public
public interface AtomicType<T> {

    /**
     * Creates a comparator for this type.
     *
     * @param sortOrderAscending True, if the comparator should define the order to be ascending,
     *                           false, if the comparator should define the order to be descending.
     * @param executionConfig The config from which the comparator will be parametrized. Parametrization
     *                        includes for example registration of class tags for frameworks like Kryo.
     * @return A comparator for this type.
     */
    TypeComparator<T> createComparator(boolean sortOrderAscending, ExecutionConfig executionConfig);
}

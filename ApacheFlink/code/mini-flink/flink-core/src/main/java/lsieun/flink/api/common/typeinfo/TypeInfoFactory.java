package lsieun.flink.api.common.typeinfo;


import java.lang.reflect.Type;
import java.util.Map;

import lsieun.flink.annotation.Public;

/**
 * Base class for implementing a type information factory. A type information factory allows for
 * plugging-in user-defined {@link TypeInformation} into the Flink type system. The factory is
 * called during the type extraction phase if the corresponding type has been annotated with
 * {@link TypeInfo}. In a hierarchy of types the closest factory will be chosen while traversing
 * upwards, however, a globally registered factory has highest precedence
 * (see {@link TypeExtractor#registerFactory(Type, Class)}).
 *
 * @param <T> type for which {@link TypeInformation} is created
 */
@Public
public abstract class TypeInfoFactory<T> {

    /**
     * Creates type information for the type the factory is targeted for. The parameters provide
     * additional information about the type itself as well as the type's generic type parameters.
     *
     * @param t the exact type the type information is created for; might also be a subclass of &lt;T&gt;
     * @param genericParameters mapping of the type's generic type parameters to type information
     *                          extracted with Flink's type extraction facilities; null values
     *                          indicate that type information could not be extracted for this parameter
     * @return type information for the type the factory is targeted for
     */
    public abstract TypeInformation<T> createTypeInfo(Type t, Map<String, TypeInformation<?>> genericParameters);

}

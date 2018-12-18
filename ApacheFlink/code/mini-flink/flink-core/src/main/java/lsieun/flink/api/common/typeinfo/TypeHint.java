package lsieun.flink.api.common.typeinfo;


import lsieun.flink.annotation.Public;
import lsieun.flink.api.common.functions.InvalidTypesException;
import lsieun.flink.util.FlinkRuntimeException;

/**
 * A utility class for describing generic types. It can be used to obtain a type information via:
 *
 * <pre>{@code
 * TypeInformation<Tuple2<String, Long>> info = TypeInformation.of(new TypeHint<Tuple2<String, Long>>(){});
 * }</pre>
 * or
 * <pre>{@code
 * TypeInformation<Tuple2<String, Long>> info = new TypeHint<Tuple2<String, Long>>(){}.getTypeInfo();
 * }</pre>
 *
 * @param <T> The type information to hint.
 */
@Public
public abstract class TypeHint<T> {

    /** The type information described by the hint. */
    private final TypeInformation<T> typeInfo;

    /**
     * Creates a hint for the generic type in the class signature.
     */
    public TypeHint() {
        try {
            this.typeInfo = TypeExtractor.createTypeInfo(
                    this, TypeHint.class, getClass(), 0);
        }
        catch (InvalidTypesException e) {
            throw new FlinkRuntimeException("The TypeHint is using a generic variable." +
                    "This is not supported, generic types must be fully specified for the TypeHint.");
        }
    }

    // ------------------------------------------------------------------------

    /**
     * Gets the type information described by this TypeHint.
     * @return The type information described by this TypeHint.
     */
    public TypeInformation<T> getTypeInfo() {
        return typeInfo;
    }

    // ------------------------------------------------------------------------

    @Override
    public int hashCode() {
        return typeInfo.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this ||
                obj instanceof TypeHint && this.typeInfo.equals(((TypeHint<?>) obj).typeInfo);
    }

    @Override
    public String toString() {
        return "TypeHint: " + typeInfo;
    }
}

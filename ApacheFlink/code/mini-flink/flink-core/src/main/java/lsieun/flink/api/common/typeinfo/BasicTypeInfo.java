package lsieun.flink.api.common.typeinfo;


import static lsieun.flink.util.Preconditions.checkNotNull;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lsieun.flink.annotation.Public;
import lsieun.flink.annotation.PublicEvolving;
import lsieun.flink.api.common.ExecutionConfig;
import lsieun.flink.api.common.functions.InvalidTypesException;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.api.common.typeutils.TypeSerializer;
import lsieun.flink.api.common.typeutils.base.BigDecComparator;
import lsieun.flink.api.common.typeutils.base.BigDecSerializer;
import lsieun.flink.api.common.typeutils.base.BigIntComparator;
import lsieun.flink.api.common.typeutils.base.BigIntSerializer;
import lsieun.flink.api.common.typeutils.base.BooleanComparator;
import lsieun.flink.api.common.typeutils.base.BooleanSerializer;
import lsieun.flink.api.common.typeutils.base.ByteComparator;
import lsieun.flink.api.common.typeutils.base.ByteSerializer;
import lsieun.flink.api.common.typeutils.base.CharComparator;
import lsieun.flink.api.common.typeutils.base.CharSerializer;
import lsieun.flink.api.common.typeutils.base.DateSerializer;
import lsieun.flink.api.common.typeutils.base.DoubleComparator;
import lsieun.flink.api.common.typeutils.base.DoubleSerializer;
import lsieun.flink.api.common.typeutils.base.FloatComparator;
import lsieun.flink.api.common.typeutils.base.FloatSerializer;
import lsieun.flink.api.common.typeutils.base.InstantComparator;
import lsieun.flink.api.common.typeutils.base.InstantSerializer;
import lsieun.flink.api.common.typeutils.base.IntComparator;
import lsieun.flink.api.common.typeutils.base.IntSerializer;
import lsieun.flink.api.common.typeutils.base.LongComparator;
import lsieun.flink.api.common.typeutils.base.LongSerializer;
import lsieun.flink.api.common.typeutils.base.ShortComparator;
import lsieun.flink.api.common.typeutils.base.ShortSerializer;
import lsieun.flink.api.common.typeutils.base.StringComparator;
import lsieun.flink.api.common.typeutils.base.StringSerializer;
import lsieun.flink.api.common.typeutils.base.VoidSerializer;

/**
 * Type information for primitive types (int, long, double, byte, ...), String, Date, Void,
 * BigInteger, and BigDecimal.
 */
@Public
public class BasicTypeInfo<T> extends TypeInformation<T> implements AtomicType<T> {

    private static final long serialVersionUID = -430955220409131770L;

    public static final BasicTypeInfo<String> STRING_TYPE_INFO = new BasicTypeInfo<>(String.class, new Class<?>[]{}, StringSerializer.INSTANCE, StringComparator.class);
    public static final BasicTypeInfo<Boolean> BOOLEAN_TYPE_INFO = new BasicTypeInfo<>(Boolean.class, new Class<?>[]{}, BooleanSerializer.INSTANCE, BooleanComparator.class);
    public static final BasicTypeInfo<Byte> BYTE_TYPE_INFO = new IntegerTypeInfo<>(Byte.class, new Class<?>[]{Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class}, ByteSerializer.INSTANCE, ByteComparator.class);
    public static final BasicTypeInfo<Short> SHORT_TYPE_INFO = new IntegerTypeInfo<>(Short.class, new Class<?>[]{Integer.class, Long.class, Float.class, Double.class, Character.class}, ShortSerializer.INSTANCE, ShortComparator.class);
    public static final BasicTypeInfo<Integer> INT_TYPE_INFO = new IntegerTypeInfo<>(Integer.class, new Class<?>[]{Long.class, Float.class, Double.class, Character.class}, IntSerializer.INSTANCE, IntComparator.class);
    public static final BasicTypeInfo<Long> LONG_TYPE_INFO = new IntegerTypeInfo<>(Long.class, new Class<?>[]{Float.class, Double.class, Character.class}, LongSerializer.INSTANCE, LongComparator.class);
    public static final BasicTypeInfo<Float> FLOAT_TYPE_INFO = new FractionalTypeInfo<>(Float.class, new Class<?>[]{Double.class}, FloatSerializer.INSTANCE, FloatComparator.class);
    public static final BasicTypeInfo<Double> DOUBLE_TYPE_INFO = new FractionalTypeInfo<>(Double.class, new Class<?>[]{}, DoubleSerializer.INSTANCE, DoubleComparator.class);
    public static final BasicTypeInfo<Character> CHAR_TYPE_INFO = new BasicTypeInfo<>(Character.class, new Class<?>[]{}, CharSerializer.INSTANCE, CharComparator.class);
    public static final BasicTypeInfo<Date> DATE_TYPE_INFO = new BasicTypeInfo<>(Date.class, new Class<?>[]{}, DateSerializer.INSTANCE, DateComparator.class);
    public static final BasicTypeInfo<Void> VOID_TYPE_INFO = new BasicTypeInfo<>(Void.class, new Class<?>[]{}, VoidSerializer.INSTANCE, null);
    public static final BasicTypeInfo<BigInteger> BIG_INT_TYPE_INFO = new BasicTypeInfo<>(BigInteger.class, new Class<?>[]{}, BigIntSerializer.INSTANCE, BigIntComparator.class);
    public static final BasicTypeInfo<BigDecimal> BIG_DEC_TYPE_INFO = new BasicTypeInfo<>(BigDecimal.class, new Class<?>[]{}, BigDecSerializer.INSTANCE, BigDecComparator.class);
    public static final BasicTypeInfo<Instant> INSTANT_TYPE_INFO = new BasicTypeInfo<>(Instant.class, new Class<?>[]{}, InstantSerializer.INSTANCE, InstantComparator.class);


    // --------------------------------------------------------------------------------------------

    private final Class<T> clazz;

    private final TypeSerializer<T> serializer;

    private final Class<?>[] possibleCastTargetTypes;

    private final Class<? extends TypeComparator<T>> comparatorClass;

    protected BasicTypeInfo(Class<T> clazz, Class<?>[] possibleCastTargetTypes, TypeSerializer<T> serializer, Class<? extends TypeComparator<T>> comparatorClass) {
        this.clazz = checkNotNull(clazz);
        this.possibleCastTargetTypes = checkNotNull(possibleCastTargetTypes);
        this.serializer = checkNotNull(serializer);
        // comparator can be null as in VOID_TYPE_INFO
        this.comparatorClass = comparatorClass;
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Returns whether this type should be automatically casted to
     * the target type in an arithmetic operation.
     */
    @PublicEvolving
    public boolean shouldAutocastTo(BasicTypeInfo<?> to) {
        for (Class<?> possibleTo: possibleCastTargetTypes) {
            if (possibleTo.equals(to.getTypeClass())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @PublicEvolving
    public boolean isBasicType() {
        return true;
    }

    @Override
    @PublicEvolving
    public boolean isTupleType() {
        return false;
    }

    @Override
    @PublicEvolving
    public int getArity() {
        return 1;
    }

    @Override
    @PublicEvolving
    public int getTotalFields() {
        return 1;
    }

    @Override
    @PublicEvolving
    public Class<T> getTypeClass() {
        return this.clazz;
    }

    @Override
    @PublicEvolving
    public boolean isKeyType() {
        return true;
    }

    @Override
    @PublicEvolving
    public TypeSerializer<T> createSerializer(ExecutionConfig executionConfig) {
        return this.serializer;
    }

    @Override
    @PublicEvolving
    public TypeComparator<T> createComparator(boolean sortOrderAscending, ExecutionConfig executionConfig) {
        if (comparatorClass != null) {
            return instantiateComparator(comparatorClass, sortOrderAscending);
        } else {
            throw new InvalidTypesException("The type " + clazz.getSimpleName() + " cannot be used as a key.");
        }
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        return (31 * Objects.hash(clazz, serializer, comparatorClass)) + Arrays.hashCode(possibleCastTargetTypes);
    }

    @Override
    public boolean canEqual(Object obj) {
        return obj instanceof BasicTypeInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BasicTypeInfo) {
            @SuppressWarnings("unchecked")
            BasicTypeInfo<T> other = (BasicTypeInfo<T>) obj;

            return other.canEqual(this) &&
                    this.clazz == other.clazz &&
                    serializer.equals(other.serializer) &&
                    this.comparatorClass == other.comparatorClass;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return clazz.getSimpleName();
    }

    // --------------------------------------------------------------------------------------------

    @PublicEvolving
    public static <X> BasicTypeInfo<X> getInfoFor(Class<X> type) {
        if (type == null) {
            throw new NullPointerException();
        }

        @SuppressWarnings("unchecked")
        BasicTypeInfo<X> info = (BasicTypeInfo<X>) TYPES.get(type);
        return info;
    }

    private static <X> TypeComparator<X> instantiateComparator(Class<? extends TypeComparator<X>> comparatorClass, boolean ascendingOrder) {
        try {
            Constructor<? extends TypeComparator<X>> constructor = comparatorClass.getConstructor(boolean.class);
            return constructor.newInstance(ascendingOrder);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not initialize basic comparator " + comparatorClass.getName(), e);
        }
    }

    private static final Map<Class<?>, BasicTypeInfo<?>> TYPES = new HashMap<Class<?>, BasicTypeInfo<?>>();

    static {
        TYPES.put(String.class, STRING_TYPE_INFO);
        TYPES.put(Boolean.class, BOOLEAN_TYPE_INFO);
        TYPES.put(boolean.class, BOOLEAN_TYPE_INFO);
        TYPES.put(Byte.class, BYTE_TYPE_INFO);
        TYPES.put(byte.class, BYTE_TYPE_INFO);
        TYPES.put(Short.class, SHORT_TYPE_INFO);
        TYPES.put(short.class, SHORT_TYPE_INFO);
        TYPES.put(Integer.class, INT_TYPE_INFO);
        TYPES.put(int.class, INT_TYPE_INFO);
        TYPES.put(Long.class, LONG_TYPE_INFO);
        TYPES.put(long.class, LONG_TYPE_INFO);
        TYPES.put(Float.class, FLOAT_TYPE_INFO);
        TYPES.put(float.class, FLOAT_TYPE_INFO);
        TYPES.put(Double.class, DOUBLE_TYPE_INFO);
        TYPES.put(double.class, DOUBLE_TYPE_INFO);
        TYPES.put(Character.class, CHAR_TYPE_INFO);
        TYPES.put(char.class, CHAR_TYPE_INFO);
        TYPES.put(Date.class, DATE_TYPE_INFO);
        TYPES.put(Void.class, VOID_TYPE_INFO);
        TYPES.put(void.class, VOID_TYPE_INFO);
        TYPES.put(BigInteger.class, BIG_INT_TYPE_INFO);
        TYPES.put(BigDecimal.class, BIG_DEC_TYPE_INFO);
        TYPES.put(Instant.class, INSTANT_TYPE_INFO);
    }
}

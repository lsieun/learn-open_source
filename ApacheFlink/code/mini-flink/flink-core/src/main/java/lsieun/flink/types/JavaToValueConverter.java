package lsieun.flink.types;

import lsieun.flink.annotation.PublicEvolving;

@PublicEvolving
public class JavaToValueConverter {

	public static Value convertBoxedJavaType(Object boxed) {
		if (boxed == null) {
			return null;
		}
		
		final Class<?> clazz = boxed.getClass();
		
		if (clazz == String.class) {
			return new StringValue((String) boxed);
		}
		else if (clazz == Integer.class) {
			return new IntValue((Integer) boxed);
		}
		else if (clazz == Long.class) {
			return new LongValue((Long) boxed);
		}
		else if (clazz == Double.class) {
			return new DoubleValue((Double) boxed);
		}
		else if (clazz == Float.class) {
			return new FloatValue((Float) boxed);
		}
		else if (clazz == Boolean.class) {
			return new BooleanValue((Boolean) boxed);
		}
		else if (clazz == Byte.class) {
			return new ByteValue((Byte) boxed);
		}
		else if (clazz == Short.class) {
			return new ShortValue((Short) boxed);
		}
		else if (clazz == Character.class) {
			return new CharValue((Character) boxed);
		}
		else {
			throw new IllegalArgumentException("Object is no primitive Java type.");
		}
	}
	
	public static Object convertValueType(Value value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof StringValue) {
			return ((StringValue) value).getValue();
		}
		else if (value instanceof IntValue) {
			return ((IntValue) value).getValue();
		}
		else if (value instanceof  LongValue) {
			return ((LongValue) value).getValue();
		}
		else if (value instanceof DoubleValue) {
			return ((DoubleValue) value).getValue();
		}
		else if (value instanceof FloatValue) {
			return ((FloatValue) value).getValue();
		}
		else if (value instanceof BooleanValue) {
			return ((BooleanValue) value).getValue();
		}
		else if (value instanceof ByteValue) {
			return ((ByteValue) value).getValue();
		}
		else if (value instanceof ShortValue) {
			return ((ShortValue) value).getValue();
		}
		else if (value instanceof CharValue) {
			return ((CharValue) value).getValue();
		}
		else {
			throw new IllegalArgumentException("Object is no primitive Java type.");
		}
	}
}

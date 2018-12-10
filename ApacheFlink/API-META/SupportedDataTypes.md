# Supported Data Types

ULR: https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/api_concepts.html#supported-data-types

Flink places some restrictions on the type of elements that can be in a `DataSet` or `DataStream`. The reason for this is that the system analyzes the types to determine efficient execution strategies.

There are **six** different categories of data types:

> 这不是明明7个吗？怎么是six呢？

- (1) Java Tuples and Scala Case Classes
- (2) Java POJOs
- (3) Primitive Types
- (4) Regular Classes
- (5) Values
- (6) Hadoop Writables
- (7) Special Types


# 1. Tuples and Case Classes

Tuples are composite types that contain a fixed number of fields with various types. The Java API provides classes from `Tuple1` up to `Tuple25`. Every field of a tuple can be an arbitrary Flink type including further tuples, resulting in nested tuples. Fields of a tuple can be accessed directly using the field’s name as `tuple.f4`, or using the generic getter method `tuple.getField(int position)`. The field indices start at `0`. Note that this stands in contrast to the Scala tuples, but it is more consistent with Java’s general indexing.

```java
DataStream<Tuple2<String, Integer>> wordCounts = env.fromElements(
    new Tuple2<String, Integer>("hello", 1),
    new Tuple2<String, Integer>("world", 2));

wordCounts.map(new MapFunction<Tuple2<String, Integer>, Integer>() {
    @Override
    public Integer map(Tuple2<String, Integer> value) throws Exception {
        return value.f1;
    }
});

wordCounts.keyBy(0); // also valid .keyBy("f0")
```

# 2. POJOs

Java and Scala classes are treated by Flink as a special POJO data type if they fulfill the following requirements:

- The class must be public.

- It must have a public constructor without arguments (default constructor).

- All fields are either public or must be accessible through getter and setter functions. For a field called `foo` the getter and setter methods must be named `getFoo()` and `setFoo()`.

- The type of a field must be supported by Flink. At the moment, Flink uses **Avro** to serialize arbitrary objects (such as Date).

Flink analyzes the structure of POJO types, i.e., it learns about the fields of a POJO. As a result POJO types are easier to use than general types. Moreover, Flink can process POJOs more efficiently than general types.

The following example shows a simple POJO with two public fields.

```java
public class WordWithCount {

    public String word;
    public int count;

    public WordWithCount() {}

    public WordWithCount(String word, int count) {
        this.word = word;
        this.count = count;
    }
}

DataStream<WordWithCount> wordCounts = env.fromElements(
    new WordWithCount("hello", 1),
    new WordWithCount("world", 2));

wordCounts.keyBy("word"); // key by field expression "word"
```

# 3. Primitive Types

Flink supports all Java and Scala **primitive types** such as `Integer`, `String`, and `Double`.

# 4. General Class Types

Flink supports most Java and Scala classes (API and custom). Restrictions apply to classes containing fields that cannot be serialized, like file pointers, I/O streams, or other native resources. Classes that follow the Java Beans conventions work well in general.

All classes that are not identified as POJO types (see POJO requirements above) are handled by Flink as general class types. Flink treats these data types as black boxes and is not able to access their content (i.e., for efficient sorting). General types are de/serialized using the serialization framework **Kryo**.

# 5. Values

Value types describe their serialization and deserialization manually. Instead of going through a general purpose serialization framework, they provide custom code for those operations by means of implementing the `org.apache.flinktypes.Value` interface with the methods `read` and `write`. Using a Value type is reasonable when general purpose serialization would be highly inefficient. An example would be a data type that implements a sparse vector of elements as an array. Knowing that the array is mostly zero, one can use a special encoding for the non-zero elements, while the general purpose serialization would simply write all array elements.

The `org.apache.flinktypes.CopyableValue` interface supports manual internal cloning logic in a similar way.

Flink comes with pre-defined Value types that correspond to basic data types. (`ByteValue`, `ShortValue`, `IntValue`, `LongValue`, `FloatValue`, `DoubleValue`, `StringValue`, `CharValue`, `BooleanValue`). These Value types act as mutable variants of the basic data types: Their value can be altered, allowing programmers to reuse objects and take pressure off the garbage collector.

# 6. Hadoop Writables

You can use types that implement the `org.apache.hadoop.Writable` interface. The serialization logic defined in the `write()` and `readFields()` methods will be used for serialization.

# 7. Special Types

You can use special types, including Scala’s `Either`, `Option`, and `Try`. The Java API has its own custom implementation of `Either`. Similarly to Scala’s `Either`, it represents a value of one two possible types, `Left` or `Right`. `Either` can be useful for error handling or operators that need to output two different types of records.

# 8. Type Erasure & Type Inference

Note: This Section is only relevant for Java.

The Java compiler throws away much of the generic type information after compilation. This is known as type erasure in Java. It means that at runtime, an instance of an object does not know its generic type any more. For example, instances of `DataStream<String>` and `DataStream<Long>` look the same to the JVM.

Flink requires type information at the time when it prepares the program for execution (when the main method of the program is called). The Flink Java API tries to reconstruct the type information that was thrown away in various ways and store it explicitly in the data sets and operators. You can retrieve the type via `DataStream.getType()`. The method returns an instance of `TypeInformation`, which is Flink’s internal way of representing types.

The type inference has its limits and needs the “cooperation” of the programmer in some cases. Examples for that are methods that create data sets from collections, such as `ExecutionEnvironment.fromCollection()`, where you can pass an argument that describes the type. But also generic functions like `MapFunction<I, O>` may need extra type information.

The `ResultTypeQueryable` interface can be implemented by input formats and functions to tell the API explicitly about their return type. The input types that the functions are invoked with can usually be inferred by the result types of the previous operations.



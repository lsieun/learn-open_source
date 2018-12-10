# Specifying Keys

Some transformations (`join`, `coGroup`, `keyBy`, `groupBy`) require that a key be defined on a collection of elements. Other transformations (`Reduce`, `GroupReduce`, `Aggregate`, `Windows`) allow data being grouped on a key before they are applied.

A **DataSet** is grouped as

```java
DataSet<...> input = // [...]
DataSet<...> reduced = input
  .groupBy(/*define key here*/)
  .reduceGroup(/*do something*/);
```

while a key can be specified on a **DataStream** using

```java
DataStream<...> input = // [...]
DataStream<...> windowed = input
  .keyBy(/*define key here*/)
  .window(/*window specification*/);
```

**The data model of Flink is not based on key-value pairs**. Therefore, you do not need to physically pack the data set types into keys and values. **Keys are “virtual”**: they are defined as functions over the actual data to guide the grouping operator.

> 这段很重要，是我之前不知道的地方。  
> Flink的data model并不是依赖于key-value pair的数据组合，它的key是通过function来选取出来的，这么说来，算是很灵活的方式。

NOTE: For **the DataStream API**, we use `DataStream` and `keyBy`. For **the DataSet API** we just replace by `DataSet` and `groupBy`.


## Define keys for Tuples

The simplest case is grouping Tuples on one or more **fields** of the Tuple:

```java
DataStream<Tuple3<Integer,String,Long>> input = // [...]
KeyedStream<Tuple3<Integer,String,Long>,Tuple> keyed = input.keyBy(0)
```

The tuples are grouped on the first field (the one of Integer type).

```java
DataStream<Tuple3<Integer,String,Long>> input = // [...]
KeyedStream<Tuple3<Integer,String,Long>,Tuple> keyed = input.keyBy(0,1)
```

Here, we group the tuples on a composite key consisting of the first and the second field.

A note on nested Tuples: If you have a DataStream with a nested tuple, such as:

```java
DataStream<Tuple3<Tuple2<Integer, Float>,String,Long>> ds;
```

Specifying `keyBy(0)` will cause the system to use the full `Tuple2` as a key (with the Integer and Float being the key). If you want to “navigate” into the nested `Tuple2`, you have to use **field expression** keys which are explained below.

## Define keys using Field Expressions

You can use **String-based field expressions** to reference nested fields and define keys for grouping, sorting, joining, or coGrouping.

Field expressions make it very easy to select fields in (nested) composite types such as `Tuple` and `POJO` types.

In the example below, we have a `WC` POJO with two fields “`word`” and “`count`”. To group by the field `word`, we just pass its name to the `keyBy()` function.

```java
// some ordinary POJO (Plain old Java Object)
public class WC {
  public String word;
  public int count;
}
DataStream<WC> words = // [...]
DataStream<WC> wordCounts = words.keyBy("word").window(/*window specification*/);
```

Field Expression Syntax:

- Select **POJO fields** by their field name. For example "user" refers to the “user” field of a POJO type.

- Select **Tuple fields** by their field name or 0-offset field index. For example "f0" and "5" refer to the first and sixth field of a Java Tuple type, respectively.

- You can select **nested fields** in POJOs and Tuples. For example "user.zip" refers to the “zip” field of a POJO which is stored in the “user” field of a POJO type. Arbitrary nesting and mixing of POJOs and Tuples is supported such as "f1.user.zip" or "user.f3.1.zip".

- You can select **the full type** using the "`*`" wildcard expressions. This does also work for types which are not Tuple or POJO types.

Field Expression Example:

```java
public static class WC {
  public ComplexNestedClass complex; //nested POJO
  private int count;
  // getter / setter for private field (count)
  public int getCount() {
    return count;
  }
  public void setCount(int c) {
    this.count = c;
  }
}
public static class ComplexNestedClass {
  public Integer someNumber;
  public float someFloat;
  public Tuple3<Long, Long, String> word;
  public IntWritable hadoopCitizen;
}
```
These are valid field expressions for the example code above:

- "count": The count field in the WC class.
- "complex": Recursively selects all fields of the field complex of POJO type ComplexNestedClass.
- "complex.word.f2": Selects the last field of the nested Tuple3.
- "complex.hadoopCitizen": Selects the Hadoop IntWritable type.


## Define keys using Key Selector Functions

An additional way to define keys are “**key selector**” functions. A key selector function takes a single element as input and returns the key for the element. The key can be of any type and be derived from deterministic computations.

The following example shows a key selector function that simply returns the field of an object:

```java
// some ordinary POJO
public class WC {public String word; public int count;}
DataStream<WC> words = // [...]
KeyedStream<WC> keyed = words
  .keyBy(new KeySelector<WC, String>() {
     public String getKey(WC wc) { return wc.word; }
   });
```


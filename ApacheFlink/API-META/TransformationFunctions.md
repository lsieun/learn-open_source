# Specifying Transformation Functions

Most transformations require **user-defined functions**. This section lists different ways of how they can be specified

## Implementing an interface

The most basic way is to implement one of the provided interfaces:

```java
class MyMapFunction implements MapFunction<String, Integer> {
  public Integer map(String value) { return Integer.parseInt(value); }
};
data.map(new MyMapFunction());
```

## Anonymous classes

You can pass a function as an anonymous class:

```java
data.map(new MapFunction<String, Integer> () {
  public Integer map(String value) { return Integer.parseInt(value); }
});
```

## Java 8 Lambdas

Flink also supports Java 8 Lambdas in the Java API.

```java
data.filter(s -> s.startsWith("http://"));
data.reduce((i1,i2) -> i1 + i2);
```

## Rich functions

All transformations that require **a user-defined function** can instead take as argument **a rich function**. For example, instead of

```java
class MyMapFunction implements MapFunction<String, Integer> {
  public Integer map(String value) { return Integer.parseInt(value); }
};
```

> 注意：上面的代码中，使用的是`MapFunction`；下面的代码中，使用的是`RichMapFunction`。

you can write

```java
class MyMapFunction extends RichMapFunction<String, Integer> {
  public Integer map(String value) { return Integer.parseInt(value); }
};
```

and pass the function as usual to a `map` transformation:

```java
data.map(new MyMapFunction());
```

Rich functions can also be defined as an anonymous class:

```java
data.map (new RichMapFunction<String, Integer>() {
  public Integer map(String value) { return Integer.parseInt(value); }
});
```

Rich functions provide, in addition to **the user-defined function** (`map`, `reduce`, etc), **four methods**: `open`, `close`, `getRuntimeContext`, and `setRuntimeContext`. These are useful for parameterizing the function (see [Passing Parameters to Functions](https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/batch/index.html#passing-parameters-to-functions)), creating and finalizing local state, accessing broadcast variables (see [Broadcast Variables](https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/batch/index.html#broadcast-variables)), and for accessing runtime information such as accumulators and counters (see [Accumulators and Counters](https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/api_concepts.html#accumulators--counters)), and information on iterations (see [Iterations](https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/batch/iterations.html)).


# Anatomy of a Flink Program

Flink programs look like regular programs that transform collections of data. Each program consists of the same basic parts:

- (1) Obtain an execution environment,
- (2) Load/create the initial data,
- (3) Specify transformations on this data,
- (4) Specify where to put the results of your computations,
- (5) Trigger the program execution

# 1. Obtain an execution environment

The `StreamExecutionEnvironment` is the basis for all Flink programs. You can obtain one using these static methods on `StreamExecutionEnvironment`:

```java
getExecutionEnvironment()

createLocalEnvironment()

createRemoteEnvironment(String host, int port, String... jarFiles)
```

Typically, you only need to use `getExecutionEnvironment()`, since this will do the right thing depending on the context: 
- If you are executing your program inside an IDE or as a regular Java program it will create a local environment that will execute your program on your local machine. 
- If you created a JAR file from your program, and invoke it through the command line, the Flink cluster manager will execute your main method and `getExecutionEnvironment()` will return an execution environment for executing your program on a cluster.

# 2. Load/create the initial data

For specifying data sources the execution environment has several methods to read from files using various methods: 
- you can just read them line by line, as CSV files, or 
- using completely custom data input formats. 
 
To just read a text file as a sequence of lines, you can use:

```java
final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

DataStream<String> text = env.readTextFile("file:///path/to/file");
```

This will give you a DataStream on which you can then apply transformations to create new derived DataStreams.

# 3. Specify transformations on this data

You apply transformations by calling methods on DataStream with a transformation functions. For example, a `map` transformation looks like this:

```java
DataStream<String> input = ...;

DataStream<Integer> parsed = input.map(new MapFunction<String, Integer>() {
    @Override
    public Integer map(String value) {
        return Integer.parseInt(value);
    }
});
```

This will create a new DataStream by converting every String in the original collection to an Integer.

# 4. Specify where to put the results of your computations

Once you have a DataStream containing your final results, you can write it to an outside system by creating a sink. These are just some example methods for creating a sink:

```java
writeAsText(String path)

print()
```

# 5. Trigger the program execution

Once you specified the complete program you need to trigger the program execution by calling `execute()` on the `StreamExecutionEnvironment`. Depending on the type of the ExecutionEnvironment the execution will be triggered on your local machine or submit your program for execution on a cluster.

The `execute()` method is returning a `JobExecutionResult`, this contains execution times and accumulator results.








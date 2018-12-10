# Accumulators & Counters

URL: https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/api_concepts.html#accumulators--counters

Accumulators are simple constructs with an **add operation** and a **final accumulated result**, which is available after the job ended.

The most straightforward accumulator is a **counter**: You can increment it using the `Accumulator.add(V value)` method. At the end of the job Flink will sum up (merge) all partial results and send the result to the client. Accumulators are useful during debugging or if you quickly want to find out more about your data.

Flink currently has the following **built-in accumulators**. Each of them implements the `Accumulator` interface.

- `IntCounter`, `LongCounter` and `DoubleCounter`: See below for an example using a counter.
- `Histogram`: A histogram implementation for a discrete number of bins. Internally it is just a map from Integer to Integer. You can use this to compute distributions of values, e.g. the distribution of words-per-line for a word count program.

## How to use accumulators:

First you have to **create an accumulator object** (here a counter) in the user-defined transformation function where you want to use it.

```java
private IntCounter numLines = new IntCounter();
```

Second you have to **register the accumulator object**, typically in the `open()` method of the rich function. Here you also define the name.

```java
getRuntimeContext().addAccumulator("num-lines", this.numLines);
```

You can now **use the accumulator** anywhere in the operator function, including in the `open()` and `close()` methods.

```java
this.numLines.add(1);
```

The overall result will be stored in the `JobExecutionResult` object which is returned from the `execute()` method of the execution environment (currently this only works if the execution waits for the completion of the job).

```java
myJobExecutionResult.getAccumulatorResult("num-lines")
```

All accumulators share a single namespace per job. Thus you can use the same accumulator in different operator functions of your job. Flink will internally merge all accumulators with the same name.

A note on accumulators and iterations: **Currently the result of accumulators is only available after the overall job has ended**. We plan to also make the result of the previous iteration available in the next iteration. You can use Aggregators to compute per-iteration statistics and base the termination of iterations on such statistics.

## Custom accumulators:

To implement your own accumulator you simply have to write your implementation of the `Accumulator` interface. Feel free to create a pull request if you think your custom accumulator should be shipped with Flink.

You have the choice to implement either `Accumulator` or `SimpleAccumulator`.

`Accumulator<V,R>` is most flexible: It defines a type `V` for the value to add, and a result type `R` for the final result. E.g. for a histogram, `V` is a number and `R` is a histogram. `SimpleAccumulator` is for the cases where both types are the same, e.g. for counters.


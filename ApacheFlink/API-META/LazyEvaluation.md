# Lazy Evaluation

All Flink programs are executed lazily: When the program’s main method is executed, the data loading and transformations do not happen directly. Rather, each operation is created and added to the program’s plan. The operations are actually executed when the execution is explicitly triggered by an `execute()` call on the **execution environment**. Whether the program is executed locally or on a cluster depends on the type of execution environment

The **lazy evaluation** lets you construct sophisticated programs that Flink executes as one holistically(整体地；全面) planned unit.



**Task managers** are **worker** nodes that execute the tasks in one or more threads in JVM.

> 一个Job应该可以拆分成许多个Task

**Executors** in Flink are defined as **task slots**. Each **Task Manager** needs to manage one or more **task slots**.

> 两个意思：  
> （1） Task Manager需要管理一个或多个task slots  
> （2） task slots就是 Executors

问题：

- 何为Executor是指一个JVM吗？
- 从Job到Task是如何拆分的呢？




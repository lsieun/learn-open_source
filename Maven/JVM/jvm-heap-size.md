# Configuring the heap size

## 1. 原理说明：Maven与JVM(heap)

Maven runs as a Java process on JVM. As Java proceeds with a build, it keeps on creating Java objects. These objects are stored in the memory allocated to Maven. This area of memory where Java objects are stored is known as **heap**. Heap is created at the JVM start, and it increases as more and more objects are created up to the
defined maximum limit.

> maven build  
> java objects -> memory(headp) 

```
代码执行过程    |    对象存储区域
package       |
test          |
compile       |     java object
              |     heap
Maven Build   |     Memory
----------------------------
             JVM
```

## 2. Maven默认heap大小

By default, the maximum heap allocation of Maven is `256-512 MB` (`-Xms256m` to `-Xmx512m`). The `-Xms` JVM flag is used to instruct JVM the minimum value it should set at the time it creates the heap. The `-Xmx` JVM flag sets the maximum heap size.

> Maven的默认JVM堆内存（heap）分配的大小为`256-512 MB`。

This default limit does not work while building a large, complex Java project, and it is recommended that you have at least `1024 MB` of maximum heap. If you encounter the `java.lang.OutOfMemoryError` error at any point during a Maven build, it is mostly due to the lack of memory. 

## 3. 如何修改heap大小

You can use the `MAVEN_OPTS` environment variable to set the maximum allowed heap size for Maven at a `global` level.

The following command will set the heap size in Linux. Make sure that the value set as the maximum heap size does not exceed your system memory of the machine that runs Maven.

```bash
$ export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=128m"
```

Here `-Xmx` takes **the maximum heap size** and `-XX:MaxPermSize` takes **the maximum PermGen size**.

**Permanent Generation** (`PermGen`) is an area of memory managed by JVM, which stores the internal representations of Java classes. The maximum size of `PermGen` can be set by the `-XX:MaxPermSize` JVM flag.

To learn about the Maven `OutOfMemoryError` error, check out the information at this link: https://cwiki.apache.org/confluence/display/MAVEN/OutOfMemoryError.
# DataSet and DataStream

## 现实层面

Depending on **the type of data sources**, i.e. **bounded** or **unbounded sources**, you would either write **a batch program** or **a streaming program** where the **DataSet API** is used for **batch** and the **DataStream API** is used for **streaming**. 

## API层面：以Class的形式来表现

Flink has the special classes `DataSet` and `DataStream` to represent data in a program. You can think of them as **immutable collections of data** that can contain duplicates. In the case of `DataSet` **the data is finite** while for a `DataStream` **the number of elements can be unbounded**.

Note that all core classes of the **Java DataSet API** are found in the package `org.apache.flink.api.java` while the classes of the **Java DataStream API** can be found in `org.apache.flink.streaming.api`.

## Flink Collection与Java Collection的不同

These collections differ from regular Java collections in some key ways. 

**First, they are immutable**, meaning that once they are created you cannot add or remove elements. You can also not simply inspect the elements inside.

> 第一，Flink Collection是immutable。

A collection is initially created by adding a source in a Flink program and new collections are derived from these by transforming them using API methods such as map, filter and so on.

> 第二，从创建方式的角度来比较


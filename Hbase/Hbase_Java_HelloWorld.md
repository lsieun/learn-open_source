# Hbase Java HelloWorld #


参考地址：  
[Windows+Eclipse+Maven+HBase 1.2.4开发环境搭建](http://blog.csdn.net/chengyuqiang/article/details/69568496)  
[HBase写入的各种方式总结汇总（代码）](https://www.cnblogs.com/teagnes/p/6446526.html)  

## 1、pom.xml ##

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.lsieun</groupId>
    <artifactId>learn-hbase</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-hdfs -->
        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-hdfs</artifactId>
            <version>2.7.3</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.hbase/hbase-client -->
        <dependency>
            <groupId>org.apache.hbase</groupId>
            <artifactId>hbase-client</artifactId>
            <version>1.2.4</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>

</project>
```

## 2、log4j.properties ##

```properties
#Define root logger options
log4j.rootLogger=INFO, file, console

#Define console appender
log4j.appender.console=org.apache.log4j.ConsoleAppender
logrj.appender.console.Target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%-5p %c{1} - %m%n

#Define rolling file appender
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=target/logs/main.log
log4j.appender.file.Append=true
log4j.appender.file.ImmediateFlush=true
log4j.appender.file.MaxFileSize=10MB
log4j.appender.file.MaxBackupIndex=5
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d %d{Z} [%t] %-5p (%F:%L) - %m%n
```

## 3、HelloWorld.java ##

```java
package com.lsieun.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class A_HelloWorld {
    private static Configuration conf = HBaseConfiguration.create();
    static {
        conf.set("hbase.rootdir", "hdfs://nns/hbase");
        // 设置Zookeeper,直接设置IP地址
        conf.set("hbase.zookeeper.quorum", "192.168.1.113:2181,192.168.1.114:2181,192.168.1.115:2181");

        try {
            File file = new File(".");
            String canonicalPath = file.getCanonicalPath();
            System.out.println("canonicalPath = " + canonicalPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 创建表
    public static void createTable(String table_name, String column_family) throws Exception{
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        TableName tableName = TableName.valueOf(table_name);
        if(admin.tableExists(tableName)){
            System.out.println("Table exists!");
            System.exit(0);
        }
        else{
            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            tableDescriptor.addFamily(new HColumnDescriptor(column_family));
            admin.createTable(tableDescriptor);
            System.out.println("create table success!");
        }

        admin.close();
        connection.close();
    }

    // 删除表
    public static void deleteTable(String table_name){
        try{
            Connection connection = ConnectionFactory.createConnection(conf);
            Admin admin = connection.getAdmin();

            TableName tableName = TableName.valueOf(table_name);
            if(!admin.tableExists(tableName)){
                System.out.println("Table is not exists!");
                System.exit(0);
            }
            else{
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
                System.out.println("delete table " + table_name + " ok.");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    //插入一行记录 scan 'testTb',{LIMIT=>5}
    public static void addRecord(String table_name, String rowKey, String family, String qualifier, String value){
        try{
            Connection connection = ConnectionFactory.createConnection(conf);
            TableName tableName = TableName.valueOf(table_name);
            Table table = connection.getTable(tableName);
            Put put = new Put(Bytes.toBytes(rowKey));
            //列簇、列、列值
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            table.close();
            connection.close();
            System.out.println("insert recored " + rowKey + " to table " + table_name + " ok.");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void testCreateTable() throws Exception {
        createTable("testTb", "info");
    }

    @Test
    public void testAddRecord(){
        addRecord("testTb", "001", "info", "name", "zhangsan");
        addRecord("testTb", "001", "info", "age", "20");
    }

    @Test
    public void testDeleteTable(){
        deleteTable("testTb");
    }


}

```

> 至此结束

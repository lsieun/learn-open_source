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

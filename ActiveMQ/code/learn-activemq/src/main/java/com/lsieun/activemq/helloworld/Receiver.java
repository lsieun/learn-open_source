package com.lsieun.activemq.helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Receiver {
    public static void main(String[] args) throws Exception {
        //第1步：建立**ConnectionFactory**工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616");

        //第2步：通过**ConnectionFactory**工厂对象创建一个**Connection**连接
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //第3步：通过Connection对象创建**Session**会话（上下文环境对象）
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

        //第4步：通过Session创建Destination对象
        Destination destination = session.createQueue("HelloWorld_Queue");

        //第5步：我们需要通过Session对象创建消息的发送和接收对象
        MessageConsumer consumer = session.createConsumer(destination);


        //第6步：客户端使用receive方法进行接收数据。
        while(true){
            TextMessage msg = (TextMessage) consumer.receive();
            if(msg == null) break;
            System.out.println("msg = " + msg.getText());
        }

        //最后不要忘记关闭**Connection**连接
        if(connection != null){
            connection.close();
        }
        System.out.println("OVER");
    }
}

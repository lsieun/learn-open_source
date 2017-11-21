package com.lsieun.activemq.helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Sender {
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
        MessageProducer producer = session.createProducer(destination);

        //第6步：我们可以使用MessageProducer的**setDeliverMode**方法为其设置持久化特性和非持久化特性（DeliveryMode）
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        //第7步：最后我们使用JMS规范的**TextMessage**形式创建数据（通过Session对象），并用MessageProducer的send方法发送数据。
        // 同理，客户端使用receive方法进行接收数据，最后不要忘记关闭**Connection**连接。
//        TextMessage textMessage = session.createTextMessage();
//        textMessage.setText("我是消息内容");
//        producer.send(textMessage);
        for(int i=0; i< 5;i++){
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("我是消息内容,id = " + (i+1));
            producer.send(textMessage);
        }

        if(connection != null){
            connection.close();
        }
        System.out.println("OVER");
    }
}

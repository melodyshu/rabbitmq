package com.example;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    private static final String EXCHANGE_NAME="exchange_demo1";
    private static final String ROUTING_KEY="routingkey_demo1";
    private static final String QUEUE_NAME="queue_demo1";
    private static final String IP_ADDRESS="192.168.230.101";
    private static final int PORT=5672;

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setUsername("admin");
        factory.setPassword("admin");
        //创建连接
        Connection connection = factory.newConnection();
        //创建通道，多路复用TCP连接
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true,false,null);
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        String message="hello world!";
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY, MessageProperties.TEXT_PLAIN,message.getBytes());
        //TimeUnit.MINUTES.sleep(60);
        channel.close();
        connection.close();


    }
}

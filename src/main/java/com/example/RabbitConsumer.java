package com.example;

import com.rabbitmq.client.*;

import javax.management.Query;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitConsumer {
    private static final String EXCHANGE_NAME="exchange_demo1";
    private static final String ROUTING_KEY="routingkey_demo1";
    private static final String QUEUE_NAME="queue_demo1";
    private static final String IP_ADDRESS="192.168.230.101";
    private static final int PORT=5672;

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //设置回调
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接受消息: "+new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(QUEUE_NAME,consumer);
        //TimeUnit.SECONDS.sleep(500);
        channel.close();
        connection.close();
    }
}

package com.example;

import com.rabbitmq.client.*;

/**
 * 发送方确认机制：1.单个confirm 2.批量confirm 3.异步confirm
 */
public class PublisherConfirm {
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
        String msg="public confirm test.";
        try {
            //将channel设置为publisher confirm模式
            channel.confirmSelect();
            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,MessageProperties.TEXT_PLAIN,msg.getBytes());
            if(!channel.waitForConfirms()){
                System.out.println("发生消息失败.");
            }

            channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,MessageProperties.TEXT_PLAIN,msg.getBytes());
            if(!channel.waitForConfirms()){
                System.out.println("发生消息失败.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        channel.close();
        connection.close();

    }
}

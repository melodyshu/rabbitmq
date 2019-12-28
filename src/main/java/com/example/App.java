package com.example;

import com.rabbitmq.client.ConnectionFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost("192.168.230.101");

    }
}

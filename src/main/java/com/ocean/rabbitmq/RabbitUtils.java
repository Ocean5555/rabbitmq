package com.ocean.rabbitmq;

import com.rabbitmq.client.Connection; import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException; import java.util.concurrent.TimeoutException;

/**

 Created by Administrator on 2019/1/10 21:42. */
public class RabbitUtils {

    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    static{
        connectionFactory.setHost("47.244.18.29");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("ocean");
        connectionFactory.setPassword("huhaiyang");
        connectionFactory.setVirtualHost("/test");
    }

    public static Connection getConnection(){

        //建立TCP连接
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
package com.ocean.rabbitmq.publish;

import com.ocean.rabbitmq.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 99512 on 2019/1/17 10:18.
 */
public class WeatherBureau {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        //第一个参数：交换机名称
        //第二个参数，队列名称
        channel.basicPublish("weather" , "" , null , "天气测试信息".getBytes());
        channel.close();
        connection.close();
    }
}

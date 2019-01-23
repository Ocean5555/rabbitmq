package com.ocean.rabbitmq.confirm;

import com.ocean.rabbitmq.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by ${Ocean} on 2019/1/17 10:53 11:03 11:04.
 */
public class Sina {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("sina", false, false, false, null);
        //第一个参数：队列名称
        //第二个参数：交换机名称
        //第三个参数：路由key
        //channel.queueBind("sina", "weather_topic", "china.henan.zhengzhou.*");
        channel.queueBind("sina", "weather_topic", "us.#");
        //处理完一次消息，取一次消息
        channel.basicQos(1);
        channel.basicConsume("sina" , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("新浪收到气象信息："+new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}

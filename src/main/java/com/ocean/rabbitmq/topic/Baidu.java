package com.ocean.rabbitmq.topic;

import com.ocean.rabbitmq.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by 99512 on 2019/1/17 10:47.
 */
public class Baidu {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("baidu", false, false, false, null);
        //第一个参数：队列名称
        //第二个参数：交换机名称
        //第三个参数：路由key
        channel.queueBind("baidu", "weather_topic", "china.shandong.qingdao.*");
        channel.queueBind("baidu", "weather_topic", "china.hebei.shijiazhuang.*");
        //解除绑定
//        channel.queueUnbind("baidu", "weather_topic", "china.hebei.shijiazhuang.*");
        //处理完一次消息，取一次消息
        channel.basicQos(1);
        channel.basicConsume("baidu" , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("百度收到气象信息："+new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}

package com.ocean.rabbitmq.workqueue;

import com.ocean.rabbitmq.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 99512 on 2019/1/17 9:28.
 */
public class SMSConsumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("sms", false, false, false , null);
        //basicQos(1)，MQ将不再一次把多个消息发功给消费者，而是在消费者处理完一个消息，发送ack确认后再发送下一个消息
        channel.basicQos(1);
        channel.basicConsume("sms", false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body);
                channel.basicAck(envelope.getDeliveryTag() , false);
                System.out.println("SMSConsumer1发送短信成功:"+jsonSMS);
            }
        });
    }
}

package com.ocean.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**

 Created by Administrator on 2019/1/10 21:41. */
public class Consumer {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("helloworld",false,false,false,null);
        //创建一个消息消费者
        //第二个参数代表是否确认收到消息，如果是false，则需要手动编程来确定收到消息
        //第三个参数:DefaultConsumer的实现类
        channel.basicConsume("helloworld",false,new Reciver(channel));

    }
}

class Reciver extends DefaultConsumer{

    private Channel channel;

    public Reciver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String messageBody = new String(body);
        System.out.println("消费者接收到:"+messageBody);
        //确认接收消息
        //第一个参数：消息的tagid
        //第二个参数：false代表只确认签收当前的消息，true代表确认签收该消费者所有未签收的消息
        channel.basicAck(envelope.getDeliveryTag(),false);

    }
}
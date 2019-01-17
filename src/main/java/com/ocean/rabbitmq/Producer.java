package com.ocean.rabbitmq;

import com.rabbitmq.client.Channel; import com.rabbitmq.client.Connection; import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException; import java.util.concurrent.TimeoutException;

/**

 Created by Administrator on 2019/1/10 21:19. */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        //创建通信“通道”，相当于tcp中的虚拟连接
        Channel channel = connection.createChannel();
        //声明并创建一个队列，如果队列已存在，则使用这个队列
        //第一个参数：队列名称ID
        //第二个参数：是否持久化，false对应不持久化数据，MQ停止后数据会丢失
        //第三个参数：是否队列私有化，false代表不私有化，所有消费者都可以使用，ture代表只有第一次拥有它的消费者才能使用。
        //第四个参数：是否自动删除，false代表连接停掉后不自动删除这个队列
        channel.queueDeclare("helloworld",false,false,false,null);
        //发送消息
        //第一个参数：交换机exchange
        //第二个参数：队列名称
        //第三个参数：额外的设置属性
        //第四个参数：需要传递的消息字节数组
        String message = "haha,ooooo";
        channel.basicPublish("","helloworld",null, message.getBytes());
        channel.close();
        connection.close();
        System.out.println("数据发送成功");

    }
}
package com.ocean.rabbitmq.workqueue;

import com.google.gson.Gson;
import com.ocean.rabbitmq.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 99512 on 2019/1/17 9:12.
 */
public class OrderSystem {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("sms", false, false, false, null);
        Gson gson = new Gson();
        for(int i=0; i<100; i++){
            SMS sms = new SMS("乘客"+i, "1520000"+i, "您的车票已经预定成功");
            String jsonSMS = gson.toJson(sms);
            channel.basicPublish("", "sms", null, jsonSMS.getBytes());
        }
        System.out.println("数据发送完成");
        channel.close();
        connection.close();
    }

}

package com.ocean.rabbitmq.confirm;

import com.ocean.rabbitmq.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by ocean on 2019/1/17 10:18.
 */
public class WeatherBureau {
    public static void main(String[] args) throws IOException, TimeoutException {
        Map area = new LinkedHashMap<String,String>();
        area.put("china.hebei.shijiazhuang.20991011" , "中国河北石家庄20991011天气数据");
        area.put("china.shandong.qingdao.20991011" , "中国山东青岛20991011天气数据");
        area.put("china.henan.zhengzhou.20991011" , "中国河南郑州20991011天气数据");
        area.put("us.cal.la.20991011" , "美国加州洛杉矶20991011天气数据");
        
        area.put("china.hebei.shijiazhuang.20991012" , "中国河北石家庄20991012天气数据");
        area.put("china.shandong.qingdao.20991012" , "中国山东青岛20991012天气数据");
        area.put("china.henan.zhengzhou.20991012" , "中国河南郑州20991012天气数据");
        area.put("us.cal.la.20991012" , "美国加州洛杉矶20991012天气数据");

        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        //开启confirm监听模式
        channel.confirmSelect();
        //交换机是否接收消息
        channel.addConfirmListener(new ConfirmListener() {
            //第二个参数代表是否批量接收
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息已被Broker接收,tag:"+deliveryTag);
            }

            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("Broker拒收消息,tag:"+deliveryTag);
            }
        });
        //交换机是否处理数据
        channel.addReturnListener(new ReturnCallback() {
            public void handle(Return returnMessage) {
                System.err.println("==========");
                System.err.println("消息无法被处理,编码：" + returnMessage.getReplyCode() + ",描述：" + returnMessage.getReplyText());
                System.err.println("交换机：" + returnMessage.getExchange() + ",路由key:" + returnMessage.getRoutingKey());
                System.err.println("消息：" + new String(returnMessage.getBody()));
                System.err.println("==========");
            }
        });

        Iterator<Map.Entry<String,String>> iterator = area.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            //第一个参数：交换机名称
            //第二个参数，路由key
            //第三个参数：mandatory，布尔值，如果消息无法正常投递，是否把消息返回给生产者，true则return回生产者，false则直接将消息丢弃
            //第五个参数：消息的字节数组
            channel.basicPublish("weather_topic" , entry.getKey() ,true , null , entry.getValue().getBytes());
        }

        /*channel.close();
        connection.close();*/
    }
}

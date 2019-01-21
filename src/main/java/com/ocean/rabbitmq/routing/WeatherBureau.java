package com.ocean.rabbitmq.routing;

import com.ocean.rabbitmq.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

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
        area.put("su.cal.la.20991011" , "美国加州洛杉矶20991011天气数据");
        
        area.put("china.hebei.shijiazhuang.20991012" , "中国河北石家庄20991012天气数据");
        area.put("china.shandong.qingdao.20991012" , "中国山东青岛20991012天气数据");
        area.put("china.henan.zhengzhou.20991012" , "中国河南郑州20991012天气数据");
        area.put("su.cal.la.20991012" , "美国加州洛杉矶20991012天气数据");

        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        Iterator<Map.Entry<String,String>> iterator = area.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            //第一个参数：交换机名称
            //第二个参数，路由key
            channel.basicPublish("weather_routing" , entry.getKey() , null , entry.getValue().getBytes());
        }
        channel.close();
        connection.close();
    }
}

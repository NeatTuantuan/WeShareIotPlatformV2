package kafka;

import netty.util.PropertiesUtil;

import javax.websocket.server.ServerEndpoint;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @ClassName SingleKafkaProps
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/3 上午10:26
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class SingleKafkaProps {
    /**
     * 单例对象
     */
    private static Properties kafkaProps = new Properties();

    private SingleKafkaProps(){}

    //获取唯一可用的对象
    public static Properties getInstance(){
        kafkaProps.put("bootstrap.servers", PropertiesUtil.getKey("KAFKA_IP"));
//        kafkaProps.put("group.id", "CountryCounter");	// 消费者群组
        // 设置序列化（自带的StringSerializer，如果消息的值为对象，就需要使用其他序列化方式，如Avro ）
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return kafkaProps;
    }

}

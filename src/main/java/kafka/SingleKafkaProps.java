package kafka;

import netty.util.PropertiesUtil;

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
    public static Properties getProducerInstance(){
        kafkaProps.put("bootstrap.servers", PropertiesUtil.getKey("KAFKA_IP"));
//        kafkaProps.put("group.id", "CountryCounter");	// 消费者群组
        // 设置序列化（自带的StringSerializer，如果消息的值为对象，就需要使用其他序列化方式，如Avro ）
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return kafkaProps;
    }

    public static Properties getConsumerInstance(){
        kafkaProps.put("bootstrap.servers", PropertiesUtil.getKey("KAFKA_IP"));
        //五位数
        kafkaProps.put("group.id","123456");
        //如果为true，则consumer的消费偏移offset会被记录到zookeeper。下次consumer启动时会从此位置继续消费。
        kafkaProps.put("enable.auto.commit", "true");
        //该参数只接受两个常量largest和Smallest,分别表示将当前offset指到日志文件的最开始位置和最近的位置。
        kafkaProps.put("auto.offset.reset", "latest");
        kafkaProps.put("auto.commit.interval.ms", "1000");

        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return kafkaProps;
    }

}

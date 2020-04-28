package kafka;

import netty.util.PropertiesUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName kafkaConsumer
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/24 上午10:14
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class kafkaConsumer {
    public static void main(String[] args) {

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(SingleKafkaProps.getConsumerInstance());
        consumer.subscribe(Arrays.asList("TESTkafka2"));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        System.out.println(records.count());
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }
}

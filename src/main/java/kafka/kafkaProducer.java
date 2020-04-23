package kafka;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import netty.util.PropertiesUtil;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

/**
 * @ClassName kafkaProducer
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/23 下午2:55
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class kafkaProducer {

    public static final String topic = "TESTkafka2";

    public static void main(String[] args){
        Timer time = new Timer();


//        Properties kafkaProps = new Properties();
//        kafkaProps.put("bootstrap.servers", PropertiesUtil.getKey("KAFKA_IP"));
//        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(SingleKafkaProps.getInstance());

        JSONObject jsonObject = new JSONObject();
        JSONObject object = new JSONObject();
        jsonObject.put("PRODUCT_ID:",123);
        jsonObject.put("DEVICE_ID:", 1001);
        jsonObject.put("formatData",object.put("参数","值"));
        String message = jsonObject.toString();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, message);


        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Future<RecordMetadata> future = producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {

                        System.out.println("offset: " + recordMetadata.offset());
                        System.out.println("topic: " + recordMetadata.topic());
                        System.out.println("partition: " + recordMetadata.partition());
                        System.out.println(recordMetadata);
                    }
                });
            }
        },100,2000);
    }
}

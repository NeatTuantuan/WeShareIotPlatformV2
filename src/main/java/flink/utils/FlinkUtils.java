package flink.utils;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @ClassName FlinkUtils
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/10 上午11:06
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class FlinkUtils {
    private static final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    public static DataStream<String> createKafkaStream(Properties kafkaProperties, DeserializationSchema deserializationSchema,String topicName ){
        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<>(topicName, new SimpleStringSchema(), kafkaProperties);
        return env.addSource(kafkaSource);
    }

    public static StreamExecutionEnvironment getEnv() {
        return env;
    }
}

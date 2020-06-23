package flink.transformation;

import flink.dao.AlarmInfo;
import flink.dao.ServiceConsumerGroup;
import flink.map.AlarmMap;
import flink.map.ServiceSubscriptionMap;
import flink.sink.PushSink;
import flink.utils.FlinkUtils;
import netty.devicemessage.DeviceMessage;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @ClassName Main
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/9 下午9:13
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class Main {
    public static void main(String[] args) throws Exception {
        //kafka参数的properties
        Properties properties = new Properties();
        //指定kafka的broker地址
        properties.setProperty("bootstrap.servers","47.103.29.15:9092");
        //groupId
        properties.setProperty("group.id","123456");
        //earliest：未纪录偏移量，从头开始消费。latest：从最新的数据开始消费
        properties.setProperty("auto.offset.reset","earliest");

        StreamExecutionEnvironment env = FlinkUtils.getEnv();
        DataStream<String> line = FlinkUtils.createKafkaStream(properties,new SimpleStringSchema(),"TESTkafka2");
        SingleOutputStreamOperator<Tuple2<DeviceMessage, ArrayList<AlarmInfo>>> alarmMap = line.map(new AlarmMap());
        SingleOutputStreamOperator<Tuple3<DeviceMessage, ArrayList<AlarmInfo>, ServiceConsumerGroup>> serviceSubscriptionMap = alarmMap.map(new ServiceSubscriptionMap());
        serviceSubscriptionMap.print();
        serviceSubscriptionMap.addSink(new PushSink());
        env.execute();
    }
}

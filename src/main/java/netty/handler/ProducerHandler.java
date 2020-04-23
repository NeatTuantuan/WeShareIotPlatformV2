package netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import kafka.SingleKafkaProps;
import netty.deviceMessage.DeviceMessage;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.Future;

/**
 * @ClassName ProducerHandler
 * @Description TODO 生产者，将数据发送致特定topic
 * @Author tuantuan
 * @Date 2020/4/3 上午10:46
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class ProducerHandler extends SimpleChannelInboundHandler<DeviceMessage> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    Producer<String, String> producer;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DeviceMessage msg) throws Exception {
        logger.info("----ProducerHandler----:"+msg.toString());

        producer = new KafkaProducer<String, String>(SingleKafkaProps.getInstance());

        //这里只放了devicemessage的getFormatData字段，
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("TESTkafka2", msg.getFormatData().toJSONString());

        Future<RecordMetadata> future = producer.send(producerRecord,(RecordMetadata recordMetadata,Exception e)->
        {
            System.out.println("offset: " + recordMetadata.offset());
            System.out.println("topic: " + recordMetadata.topic());
            System.out.println("partition: " + recordMetadata.partition());
            System.out.println(recordMetadata);
        });

    }
}

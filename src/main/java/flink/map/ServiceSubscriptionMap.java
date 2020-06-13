package flink.map;

import flink.dao.AlarmInfo;
import flink.dao.ServiceConsumerGroup;
import flink.utils.StringUtils;
import netty.devicemessage.DeviceMessage;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import redis.RedisOps;

import java.util.*;

/**
 * @ClassName ServiceSubscriptionMap
 * @Description TODO 服务端订阅map
 * @Author tuantuan
 * @Date 2020/4/10 上午11:33
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class ServiceSubscriptionMap extends RichMapFunction<Tuple2<DeviceMessage,ArrayList<AlarmInfo>>
        ,Tuple3<DeviceMessage,ArrayList<AlarmInfo>,ServiceConsumerGroup>> {
    /**
     *服务端订阅集合
     */
    private HashMap<String,ServiceConsumerGroup> consumerGroupMap = null;


    /**
     * 返回值
     */
    Tuple3 tuple3 = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public Tuple3<DeviceMessage, ArrayList<AlarmInfo>, ServiceConsumerGroup> map(Tuple2<DeviceMessage,ArrayList<AlarmInfo>> msg) throws Exception {
        consumerGroupMap = new HashMap<>(10);

        HashSet<String> keys = RedisOps.keys("*",1);

        for (String key : keys) {
            consumerGroupMap.put(key,(ServiceConsumerGroup)RedisOps.getObject(key,1));
        }


        for(HashMap.Entry<String,ServiceConsumerGroup> consumerGroupEntry : consumerGroupMap.entrySet()){
            String key = consumerGroupEntry.getKey();
            ServiceConsumerGroup group = consumerGroupMap.get(key);
            //如果某消费者组中有该设备
            if (group.getDeviceList().contains(StringUtils.getDeviceId(msg.f0.getTopic()))){
                 tuple3 = Tuple3.of(msg.f0,msg.f1,group);
            }
        }

        return tuple3;
    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}

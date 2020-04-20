package flink.map;

import flink.dao.ServiceConsumerGroup;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import redis.RedisOps;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @ClassName ServiceSubscriptionMap
 * @Description TODO 服务端订阅map
 * @Author tuantuan
 * @Date 2020/4/10 上午11:33
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class ServiceSubscriptionMap extends RichMapFunction<String,String> {

    private HashMap<String,ServiceConsumerGroup> consumerGroupMap = null;
    private Iterator it = consumerGroupMap.entrySet().iterator();

    @Override
    public void open(Configuration parameters) throws Exception {
        consumerGroupMap = new HashMap<>();
        HashSet<String> keys = RedisOps.keys("*");
        for (String key : keys) {
            consumerGroupMap.put(key,(ServiceConsumerGroup)RedisOps.getObject(key));
        }
        super.open(parameters);
    }
    @Override
    public String map(String s) throws Exception {

        while (it.hasNext()) {
            String key = (String) it.next();
            ServiceConsumerGroup group = consumerGroupMap.get(key);
            //如果某消费者组中有该设备
            if (group.getDeviceList().contains(s)){
                //根据pushType选择推送信息，在线程池异步发送消息
            }
        }

        return null;
    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}

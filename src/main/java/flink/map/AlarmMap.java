package flink.map;


import flink.dao.AlarmInfo;
import flink.dao.VariableRule;
import flink.utils.Judgement;
import flink.utils.StringUtils;
import netty.devicemessage.DeviceMessage;
import netty.util.DeviceMessageJson;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import redis.RedisConnection;
import redis.RedisOps;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @ClassName AlarmMap
 * @Description TODO 告警map
 * @Author tuantuan
 * @Date 2020/4/10 上午11:25
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class AlarmMap extends RichMapFunction<String, Tuple2<DeviceMessage,ArrayList<AlarmInfo>>> {
    /**
     * 存放告警信息实体类的集合
     */
    ArrayList<AlarmInfo> alarmInfos = null;
    /**
     * 设备信息实体类
     */
    DeviceMessage deviceMessage;
    /**
     * 规则map
     */
    HashMap<Integer, VariableRule> map;
    /**
     * 是否告警的标志
     */
    boolean isAlarm = false;
    /**
     * 设备状态：
     * 0-离线
     * 1-在线
     */
    int state = 0;

    @Override
    public void open(Configuration parameters) throws Exception {
        //获取redis链接
        super.open(parameters);
        RedisConnection.getJedis();
    }

    @Override
    public Tuple2<DeviceMessage, ArrayList<AlarmInfo>> map(String msg) throws Exception {
        //将kafka中的数据字符串转换为实体类
        deviceMessage = DeviceMessageJson.JsonToDeviceMessage(msg);

        //获取该设备对应的所有规则
<<<<<<< HEAD
        map = RedisOps.getObjectHashAll(StringUtils.getDeviceId(deviceMessage.getTopic()));
        //变量值
        String attributeValue;
=======
        HashMap<Integer, VariableRule> map = RedisOps.getObjectHashAll(StringUtils.getDeviceId(deviceMessage.getTopic()),0);
>>>>>>> 15864ac00834cb94a8f9df1684d5c5540facde6c

        alarmInfos = new ArrayList<>();

        for (HashMap.Entry<Integer, VariableRule> entry : map.entrySet()){
                VariableRule rule = entry.getValue();
            //如果包含告警字段，则根据规则判断是否告警
            if ((attributeValue = (String)deviceMessage.getVariableReport().getVariableJson().get(rule.getAttribute()))!=null){
                //判断是否告警
                isAlarm = Judgement.judge(rule,deviceMessage.getVariableReport().getVariableJson());
                //构造告警信息类并添加进集合中
                if (isAlarm == true){
                    alarmInfos.add(AlarmInfo.of(rule.getAttribute(),rule.getVariableFlag(),new String("变量:"+rule.getAttribute()+"告警:"+attributeValue)));
                }
            }
        }
        //判断设备当前在线状态
        if (deviceMessage.getDeviceShadow()!=null && deviceMessage.getDeviceShadow().getState()!=state){
            alarmInfos.add(AlarmInfo.of(null,2,deviceMessage.getDeviceShadow().getState() == 0?"设备下线":"设备上线"));
            state = deviceMessage.getDeviceShadow().getState();
        }

        return Tuple2.of(deviceMessage,alarmInfos);
    }

    @Override
    public void close() throws Exception {
        super.close();
        RedisConnection.getJedis().close();
    }
}

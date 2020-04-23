package flink.map;


import com.alibaba.fastjson.JSONObject;
import flink.dao.VariableRule;
import netty.deviceMessage.DeviceMessage;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import redis.RedisConnection;
import redis.RedisOps;

import java.util.HashMap;


/**
 * @ClassName AlarmMap
 * @Description TODO 告警map
 * @Author tuantuan
 * @Date 2020/4/10 上午11:25
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class AlarmMap extends RichMapFunction<String,String> {
    //RedisConnection里redis链接最好单例模式，节省资源
    @Override
    public void open(Configuration parameters) throws Exception {
        //获取redis链接
        super.open(parameters);
        RedisConnection.getJedis();
    }

    //这里的参数s应该是从kafka里反序列化拿出来的json串
    //json串的格式为：{"PRODUCT_ID":"XXX","DEVICE_ID":"XXX","formatData":{"temperature":"XXX","kaiguan":"XXX"}}
    @Override
    public String map(String s) throws Exception {
        //Redis当前 key="1001"中存了两条规则，分别是：
//        VariableRule(variableFlag=0, boolTypeRule=BoolTypeRule(triggerCondition=1), digitTypeRule=null, attribute=kaiguan)
//        VariableRule(variableFlag=1, boolTypeRule=null, digitTypeRule=DigitTypeRule(START_SECTION=1.0, END_SECTION=5.0, triggerCondition=2), attribute=temperature)
        //从kafka中拿数据，主要拿取设备id
        String line = s;
        System.out.println(line);
        DeviceMessage deviceMessage = new DeviceMessage();
        JSONObject jsonObject = new JSONObject().parseObject(line);
        deviceMessage.setPRODUCT_ID(jsonObject.getString("PRODUCT_ID"));
        deviceMessage.setDEVICE_ID(jsonObject.getString("DEVICE_ID"));
        deviceMessage.setFormatData(jsonObject.getJSONObject("formatData"));
        System.out.println(deviceMessage);
//        deviceMessage.setMETA_DATA();

        HashMap<Integer, VariableRule> map = RedisOps.getObjectHashAll(jsonObject.getString("DEVICE_ID"));
        if(map.size()!=0) {
            int i =0;
            for (HashMap.Entry<Integer, VariableRule> entry : map.entrySet()) {
                VariableRule rule = entry.getValue();

                boolean flag = rule.Judge(deviceMessage);
                //如果flag是true，说明满足告警条件，需要向web端发送一个http告警请求，提交给线程池异步处理
                if(flag == true){
                      i++;
                }
                else{
                    System.out.println("不符合报警规则");
                    break;
                }
            }
            if(i == map.size()){
                System.out.println("符合报警规则发出告警！需要向web端发送一个http告警请求，提交给线程池异步处理");
            }
        }
        else{
            System.out.println("无该设备相应的规则");
        }

        return s;
    }

    @Override
    public void close() throws Exception {
        super.close();
        RedisConnection.getJedis().close();
    }
}

package flink.map;

import flink.dao.ServiceSubscriptionObject;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.util.ArrayList;

/**
 * @ClassName ServiceSubscriptionMap
 * @Description TODO 服务端订阅map
 * @Author tuantuan
 * @Date 2020/4/10 上午11:33
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class ServiceSubscriptionMap extends RichMapFunction<String,String> {

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }
    @Override
    public String map(String s) throws Exception {
        //从redis获取服务端订阅实体列表
        ArrayList<ServiceSubscriptionObject> list = new ArrayList<>();
        //遍历服务端订阅实体类
        for (ServiceSubscriptionObject object : list){
            //若含有设备id
            if (object.getDeviceList().contains("s")){
                //提交给线程池异步发送http请求
            }
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}

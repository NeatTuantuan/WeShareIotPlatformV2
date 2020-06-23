package flink.sink;

import flink.dao.AlarmInfo;
import flink.dao.ConsumerClient;
import flink.dao.ServiceConsumerGroup;
import netty.devicemessage.DeviceMessage;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.util.ArrayList;

/**
 * @ClassName PushSink
 * @Description TODO 推送的sink
 * @Author tuantuan
 * @Date 2020/5/13 下午8:16
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class PushSink extends RichSinkFunction<Tuple3<DeviceMessage,ArrayList<AlarmInfo>,ServiceConsumerGroup>> {
    /**
     * 设备状态：
     * 0-离线
     * 1-在线
     */
    private int stateFlag = 0;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void invoke(Tuple3<DeviceMessage, ArrayList<AlarmInfo>, ServiceConsumerGroup> value, Context context) throws Exception {
        //向本地推送每个告警list的实体类
        for (AlarmInfo alarmInfo : value.f1) {
//            String s = HttpRequest.sendPost("http://127.0.0.1:9998/push", alarmInfo.toString());
//            System.out.println(s);
        }
        /**
         * 暂时存本地ip端口，方便测试
         */
        for (ConsumerClient consumerClient : value.f2.getClient()) {
            //推送类型中有设备上报消息
            if (value.f2.getPushType().contains(1)){
//                String s = HttpRequest.sendPost("http://" + consumerClient.getConsumerClientIP()+ ":" + consumerClient.getPort() + "/push", value.f0.getVariableReport().toString());
//                System.out.println(s);
            }
            //推送类型中有生命周期变化
            if (value.f2.getPushType().contains(2)){
                //若当前设备状态有变化，则推送信息
                if (value.f0.getDeviceShadow().getState() != stateFlag){
//                    String s = HttpRequest.sendPost("http://" + consumerClient.getConsumerClientIP()+ ":" + consumerClient.getPort() + "/push", value.f0.getDeviceShadow().toString());
//                    System.out.println(s);
                }
                //重制设备状态
                stateFlag = value.f0.getDeviceShadow().getState();
            }
            //推送类型中有设备告警
            if (value.f2.getPushType().contains(4)){
                for (AlarmInfo alarmInfo : value.f1) {
//                    String s = HttpRequest.sendPost("http://" + consumerClient.getConsumerClientIP() + ":" + consumerClient.getPort() + "/push", alarmInfo.toString());
//                    System.out.println(s);
                }
            }
        }

    }

    @Override
    public void close() throws Exception {
        super.close();
    }

}

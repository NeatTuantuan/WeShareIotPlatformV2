package netty.protocol;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import netty.devicemessage.DeviceMessage;
import netty.devicemessage.DeviceShadow;
import netty.devicemessage.VariableReport;
import netty.util.DeviceMessageJson;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ProtocolProcess {

    Logger log = LoggerFactory.getLogger(this.getClass());
    DeviceMessage deviceMessage = new DeviceMessage();
    DeviceShadow deviceShadow = new DeviceShadow();
    VariableReport variableReport = new VariableReport();

    //返回连接ack
    public void processConnectBack(ChannelHandlerContext ctx, MqttMessage msg) {
        log.info("connect");
        MqttConnectMessage message = (MqttConnectMessage)msg;
        System.out.println("connect message " + String.valueOf(message));

//       String deviceId = message.payload().clientIdentifier();
//       deviceMessage.setDEVICE_ID(deviceId);

        //回复ack
        MqttFixedHeader CONNACK_HEADER = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE,false,0);
        MqttConnAckVariableHeader variableheader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, false);
        MqttConnAckMessage mqttConnAckMessage = new MqttConnAckMessage(CONNACK_HEADER,variableheader);
        ctx.channel().writeAndFlush(mqttConnAckMessage);
    }

    public void processRecivePublishMsg(ChannelHandlerContext ctx, MqttMessage msg) {
        log.info("收到数据");
        MqttPublishMessage message = (MqttPublishMessage) msg;

        /**
         * 将消息体转换为json对象
         */
        ByteBuf payload = message.payload();
        byte[] bytes = new byte[payload.capacity()];
        payload.readBytes(bytes,0,payload.capacity());//传输此缓冲区的数据到指定的目标bytes
        String str = new String(bytes);
        JSONObject jsonStr = JSONObject.parseObject(str);

        System.out.println(jsonStr);
        final String topics = message.variableHeader().topicName();//获取topic字符串
        String[] topic = topics.split("/");

        //topic分开后从下标1开始，长度加一
        switch (topic.length){
            case 7:
                log.info("设备影子");
                if(deviceShadow.isDeviceShadow(topics)){
                    log.info("准备处理设备影子");

                    deviceMessage.setDeviceMessageType(0);
                    deviceMessage.setTopic(topics);

                    deviceShadow.setId(jsonStr.getString("id"));
                    deviceShadow.setMethod(jsonStr.getString("method"));
                    deviceShadow.setVersion(jsonStr.getString("version"));
                    deviceShadow.setState(Integer.valueOf(jsonStr.getJSONObject("params").getString("state")));

//                    System.out.println(deviceShadow.getState());
                    deviceMessage.setDeviceShadow(this.deviceShadow);

                }
                else{
                    log.warn("topic格式不匹配");
                    return;
                }
                break;
            case 9:
                log.info("变量上报");
                if(variableReport.isVariableReport(topics)){
                    log.info("准备处理变量上报");
//                    deviceMessage.setPRODUCT_ID(topic[4]);
//                    deviceMessage.setDEVICE_ID(topic[5]);
                    deviceMessage.setDeviceMessageType(1);
                    deviceMessage.setTopic(topics);

                    variableReport.setId(jsonStr.getString("id"));
                    variableReport.setMethod(jsonStr.getString("method"));
                    variableReport.setVersion(jsonStr.getString("version"));
                    variableReport.setVariableJson(jsonStr.getJSONObject("params"));

                    deviceMessage.setVariableReport(this.variableReport);
                }
                else{
                    log.warn("topic格式不匹配");
                    return;
                }
                break;
            default:
                log.warn("topic格式不匹配");
                return;
        }

//        deviceMessage.setMETA_DATA(bytes);
//        String str = new String(deviceMessage.getMETA_DATA());
//        System.out.println(str);
        ctx.fireChannelRead(deviceMessage);

    }

}

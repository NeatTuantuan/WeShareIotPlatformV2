package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import netty.devicemessage.DeviceMessage;
import netty.devicemessage.DeviceShadow;
import netty.devicemessage.VariableReport;
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
        System.out.println("connect message " +String.valueOf(message));

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
        final String topics = message.variableHeader().topicName();//获取topic字符串
        String[] topic = topics.split("/");

        //topic分开后从下标1开始，长度加一
        switch (topic.length){
            case 7:
                log.info("设备影子");
                if(deviceShadow.isDeviceShadow(topics)){
                    log.info("准备处理设备影子");
//                    deviceMessage.setPRODUCT_ID(topic[5]);
//                    deviceMessage.setDEVICE_ID(topic[6]);
                    deviceMessage.setDeviceMessageType(0);
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

        ByteBuf payload = message.payload();
        byte[] bytes = new byte[payload.capacity()];
        payload.readBytes(bytes,0,payload.capacity());
//        deviceMessage.setMETA_DATA(bytes);
//        String str = new String(deviceMessage.getMETA_DATA());
//        System.out.println(str);
        ctx.fireChannelRead(deviceMessage);

    }

}

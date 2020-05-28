package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import netty.deviceMessage.DeviceMessage;


public class ProtocolProcess {

    DeviceMessage deviceMessage = new DeviceMessage();

    //返回连接ack
    public void processConnectBack(ChannelHandlerContext ctx, MqttMessage msg) {
        System.out.println("connect");
        MqttConnectMessage message = (MqttConnectMessage)msg;
        System.out.println("connect message " +String.valueOf(message));

        String deviceId = message.payload().clientIdentifier();
        deviceMessage.setDEVICE_ID(deviceId);

        //回复ack
        MqttFixedHeader CONNACK_HEADER = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE,false,0);
        MqttConnAckVariableHeader variableheader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, false);
        MqttConnAckMessage mqttConnAckMessage = new MqttConnAckMessage(CONNACK_HEADER,variableheader);
        ctx.channel().writeAndFlush(mqttConnAckMessage);
    }

    public void processRecivePublishMsg(ChannelHandlerContext ctx, MqttMessage msg) {
        System.out.println("收到数据");
        MqttPublishMessage message = (MqttPublishMessage) msg;
        ByteBuf payload = message.payload();
        byte[] bytes = new byte[payload.capacity()];
        payload.readBytes(bytes,0,payload.capacity());
        deviceMessage.setMETA_DATA(bytes);
        ctx.fireChannelRead(deviceMessage);

    }

}

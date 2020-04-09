package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.AttributeKey;
import netty.util.Constants;

public class ClientProtocolProcess {

    public void processConnectBack(ChannelHandlerContext ctx, MqttMessage msg) {
        MqttConnectMessage message = (MqttConnectMessage)msg;
        //消息解码器出现异常
        if (message.decoderResult().isFailure()){
            Throwable cause = message.decoderResult().cause();
            if (cause instanceof  MqttUnacceptableProtocolVersionException){
                //不支持的协议版本
                MqttConnAckMessage connAckMessage = (MqttConnAckMessage) MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK,false,MqttQoS.AT_MOST_ONCE,false,0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION,false),null);
                ctx.writeAndFlush(connAckMessage);
                ctx.close();
                return;
            }else if (cause instanceof MqttIdentifierRejectedException){
                //不合格的cilentId
                MqttConnAckMessage connAckMessage = (MqttConnAckMessage)MqttMessageFactory.newMessage(
                        new MqttFixedHeader(MqttMessageType.CONNACK,false,MqttQoS.AT_MOST_ONCE,false,0),
                        new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED,false),null);
                ctx.writeAndFlush(connAckMessage);
                ctx.close();
                return;
            }
            ctx.close();
            return;
        }
        //TODO
        //会话已经存在该clientId的处理
        //遗嘱处理
        //心跳
        MqttConnAckVariableHeader variableheader = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, false);
        MqttConnAckMessage connAckMessage = new MqttConnAckMessage(Constants.CONNACK_HEADER, variableheader);
        ctx.channel().attr(AttributeKey.valueOf("clientId")).set(message.payload().clientIdentifier());
        ctx.write(connAckMessage);
    }


    public void processPubAck(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        MqttPublishMessage message = (MqttPublishMessage) mqttMessage;
        //QoS = 1
        if (message.fixedHeader().qosLevel()==MqttQoS.AT_LEAST_ONCE) {
            //返回ack
            MqttPubAckMessage pubAckMessage = (MqttPubAckMessage) MqttMessageFactory.newMessage(
                    new MqttFixedHeader(MqttMessageType.PUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                    MqttMessageIdVariableHeader.from(message.variableHeader().packetId()), null);
            ctx.channel().writeAndFlush(pubAckMessage);
        }
    }


    public void processPubRec(ChannelHandlerContext ctx, MqttMessage mqttMessage) {

    }


    public void processPubComp(ChannelHandlerContext ctx, MqttMessage mqttMessage) {

    }


    public void processPubRel(ChannelHandlerContext ctx, MqttMessage mqttMessage) {

    }



    public void processPublish(ChannelHandlerContext ctx, MqttMessage mqttMessage) {

    }

    public void processSubAck(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        MqttSubAckMessage message = (MqttSubAckMessage) mqttMessage;
        MqttSubAckMessage mqttSubAckMessage = (MqttSubAckMessage)MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.SUBACK, false, MqttQoS.AT_LEAST_ONCE, false, 0),
                MqttMessageIdVariableHeader.from(message.variableHeader().messageId()),null);
        ctx.writeAndFlush(mqttSubAckMessage);
    }


    public void processUnSubBack(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
    }
}

package netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.AttributeKey;
import netty.util.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MqttServerHandler extends SimpleChannelInboundHandler<Object> {
    public static Logger log = LogManager.getLogger(MqttServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        try
        {
            //处理mqtt消息
            if (((MqttMessage)msg).decoderResult().isSuccess())
            {
                MqttMessage req = (MqttMessage)msg;
                switch (req.fixedHeader().messageType())
                {
                    case CONNECT:
                        doConnectMessage(ctx, msg);
                        return;
                    case SUBSCRIBE:
                        doSubMessage(ctx, msg);
                        return;
                    case PUBLISH:
                        doPublishMessage(ctx, msg);
                        return;
                    case PINGREQ:
                    case PUBACK:
                    case PUBREC:
                    case PUBREL:
                    case PUBCOMP:
                    case UNSUBACK:
                    case PINGRESP:
                    case DISCONNECT:
                        ctx.close();
                        return;
                    default:
                        return;
                }
            }
        }
        catch (Exception ex)
        {

        }
    }
    private void doPublishMessage(ChannelHandlerContext ctx, Object msg){
        MqttPublishMessage message = (MqttPublishMessage) msg;
        //QoS = 1
        if (message.fixedHeader().qosLevel()==MqttQoS.AT_LEAST_ONCE){
            //返回ack
            MqttPubAckMessage pubAckMessage = (MqttPubAckMessage) MqttMessageFactory.newMessage(
                    new MqttFixedHeader(MqttMessageType.PUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0),
                    MqttMessageIdVariableHeader.from(message.variableHeader().packetId()), null);
            ctx.channel().writeAndFlush(pubAckMessage);
        }
    }
    /**
     * 处理 客户端订阅消息
     */
    private void doSubMessage(ChannelHandlerContext ctx, Object msg) {
        MqttSubscribeMessage message = (MqttSubscribeMessage)msg;
        int msgId = message.variableHeader().messageId();
        List<MqttTopicSubscription> requestSubscriptions = message.payload().topicSubscriptions();
        for (MqttTopicSubscription subscription : requestSubscriptions){
//            if (StringUtils.isEmpty(subscription.topicName())){
//                ctx.close();
//                return;
//            }
        }
//        获得相关主题的服务质量要求，用于返回码和处理保留的消息。并返回SUBACK报文
        List<Integer> grantedQosLevels = new ArrayList<>();
        requestSubscriptions.forEach(subscription ->{
            grantedQosLevels.add(subscription.qualityOfService().value());
        });

        MqttMessageIdVariableHeader header = MqttMessageIdVariableHeader.from(msgId);
        MqttSubAckPayload payload = new MqttSubAckPayload(grantedQosLevels);
        MqttSubAckMessage suback = new MqttSubAckMessage(Constants.SUBACK_HEADER, header, payload);
        ctx.write(suback);
    }

    /**
     * 处理连接请求
     */
    private void doConnectMessage(ChannelHandlerContext ctx, Object msg) {
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
}

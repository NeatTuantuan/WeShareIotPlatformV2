package netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import netty.protocol.ClientProtocolProcess;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class MqttServerHandler extends SimpleChannelInboundHandler<Object> {
    public static Logger log = LogManager.getLogger(MqttServerHandler.class);
    private ClientProtocolProcess clientProtocolProcess = new ClientProtocolProcess();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null) {
            return;
        }

        //处理mqtt消息
        if (((MqttMessage) msg).decoderResult().isSuccess()) {
            MqttMessage message = (MqttMessage) msg;
            switch (message.fixedHeader().messageType()) {
                case CONNACK:
                    clientProtocolProcess.processConnectBack(ctx, message);
                    break;
                case UNSUBACK:
                    clientProtocolProcess.processUnSubBack(ctx, message);
                    break;
                case PUBLISH:
                    clientProtocolProcess.processPublish(ctx, message);
                    break;
                case PUBACK:
                    clientProtocolProcess.processPubAck(ctx, message);
                    break;
                case PUBREC:
                    clientProtocolProcess.processPubRec(ctx, message);
                    break;
                case PUBREL:
                    clientProtocolProcess.processPubRel(ctx, message);
                    break;
                case PUBCOMP:
                    clientProtocolProcess.processPubComp(ctx, message);
                    break;
                case SUBACK:
                    clientProtocolProcess.processSubAck(ctx, message);
                    break;
                default:
                    break;
            }
        }
    }
}

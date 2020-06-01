package netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttMessage;
import netty.protocol.ProtocolProcess;


/**
 * @author lizongyin
 */
public class MqttServerHandler extends ChannelInboundHandlerAdapter {

    public ProtocolProcess protocolProcess = new ProtocolProcess();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("MqttServerHandler running.....");
        if (msg == null) {
            System.out.println("msg == null");
            return;
        }
            MqttMessage message = (MqttMessage) msg;
            switch (message.fixedHeader().messageType()) {
                case CONNECT:
                    protocolProcess.processConnectBack(ctx,message);
                    break;

                case PUBLISH:
                    protocolProcess.processRecivePublishMsg(ctx,message);
                    break;
                default:
                    break;
            }


    }

}

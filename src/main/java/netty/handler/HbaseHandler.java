package netty.handler;

import hbase.dao.CreateHbaseTable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.devicemessage.DeviceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseHandler extends SimpleChannelInboundHandler<DeviceMessage> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DeviceMessage msg) throws Exception {
        logger.info("准备创建hbase表");

        CreateHbaseTable.createhbase(msg);
    }
}

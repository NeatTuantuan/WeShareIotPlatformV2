package netty.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.devicemessage.DeviceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName EncapsulationHandler
 * @Description TODO 封装类，代替mqttServerHandler
 * @Author tuantuan
 * @Date 2020/4/23 下午6:59
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class EncapsulationHandler extends SimpleChannelInboundHandler<String> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    DeviceMessage deviceMessage;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
         deviceMessage = JSON.parseObject(msg,DeviceMessage.class);

        logger.info("----EncapsulationHandler----:"+deviceMessage.toString());

        ctx.fireChannelRead(deviceMessage);
    }
}

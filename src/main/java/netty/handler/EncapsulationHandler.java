package netty.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.deviceMessage.DeviceMessage;
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

    DeviceMessage deviceMessage = new DeviceMessage();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //转换为json对象
        JSONObject jsonObject = JSONObject.parseObject(msg);

        deviceMessage.setDEVICE_ID(jsonObject.getString("device_id"));
        deviceMessage.setPRODUCT_ID(jsonObject.getString("product_id"));
        deviceMessage.setFormatData(jsonObject.getJSONObject("format_Data"));
        deviceMessage.setMETA_DATA(jsonObject.getBytes("meta_data"));

        logger.info("----EncapsulationHandler----:"+deviceMessage.toString());

        ctx.fireChannelRead(deviceMessage);
//        ctx.writeAndFlush(deviceMessage);
    }
}

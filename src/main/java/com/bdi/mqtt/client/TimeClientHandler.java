package com.bdi.mqtt.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.devicemessage.DeviceMessage;
import netty.devicemessage.DeviceShadow;
import netty.devicemessage.VariableReport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @ClassName TimeClientHandler
 * @Description TODO
 * @Author tuantuan
 * @Date 2019/6/13 上午10:12
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
    Timer time = new Timer();
    JSONObject jsonObject = new JSONObject();

    Date date;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 当客户端和服务端 TCP 链路建立成功之后，Netty 的 NIO 线程会调用 channelActive 方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                date = new Date();
                DeviceShadow deviceShadow = new DeviceShadow("DM001", "UPDATE", "1.0", 1);

                jsonObject.put("temperature","36.5");
                jsonObject.put("humidity","12.1");
                jsonObject.put("switch_status","ON");

                VariableReport variableReport = new VariableReport("DM001", "UPDATE", "1.0", jsonObject,"test".getBytes());

                DeviceMessage deviceMessage = new DeviceMessage(1,"/weshare/iot/sys/product_id/device_id/thing/model/up_raw",deviceShadow,variableReport);

                ByteBuf respByteBuf = Unpooled.copiedBuffer(JSON.toJSONString(deviceMessage).getBytes());
                /**
                 * writeBytes：将指定的源数组的数据传输到缓冲区
                 * 调用 ChannelHandlerContext 的 writeAndFlush 方法将消息发送给服务器
                 */
                ctx.writeAndFlush(respByteBuf);
            }
        },100,5000);

    }

    /**
     * 当服务端返回应答消息时，channelRead 方法被调用，从 Netty 的 ByteBuf 中读取并打印应答消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println(Thread.currentThread().getName() + ",Server return Message：" + body);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**释放资源*/
        logger.warning("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }


}

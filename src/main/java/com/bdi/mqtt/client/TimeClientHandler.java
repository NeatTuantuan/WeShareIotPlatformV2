package com.bdi.mqtt.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
    JSONObject formatData = new JSONObject();
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 当客户端和服务端 TCP 链路建立成功之后，Netty 的 NIO 线程会调用 channelActive 方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                formatData.put("temperature",36.5);
                formatData.put("switch",1);
                formatData.put("time",simpleDateFormat.format(date));

                jsonObject.put("device_id","DEVICE-ID");
                jsonObject.put("product_id","PRODUCT-ID");
                jsonObject.put("format_Data",formatData);
                jsonObject.put("meta_data","META_DATA".getBytes());

                String reqMsg = jsonObject.toJSONString();

                ByteBuf respByteBuf = Unpooled.copiedBuffer(reqMsg.getBytes());
                /**
                 * writeBytes：将指定的源数组的数据传输到缓冲区
                 * 调用 ChannelHandlerContext 的 writeAndFlush 方法将消息发送给服务器
                 */
                ctx.writeAndFlush(respByteBuf);
            }
        },100,2000);

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

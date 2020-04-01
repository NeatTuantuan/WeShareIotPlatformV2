package com.bdi.mqtt.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;

public class MqttServerHandler {

    /**
     * @description 协议可变头部与消息体部分定义
     */
    private String clientId;//客户端唯一标识
    private String userName;
    private String password;
    private String brokerId;
    private boolean connected;
    private boolean cleanSession;//清理会话
    private int keepAlive;//空闲连接时间
    private int keepAliveMax;

    public MqttServerHandler(int keepAliveMax){
        this.keepAliveMax = keepAliveMax;
    }

    public void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception{

        try{
            /**
             * @description mqtt14种控制报文
             */
            if(msg.decoderResult().isSuccess()){
                //消息类型存放在固定头部中
                switch (msg.fixedHeader().messageType()){
                    case CONNECT:
                        onConnect(ctx, (MqttConnectMessage) msg);
                        return;
                    case CONNACK:
                        onConnack(ctx, (MqttConnAckMessage) msg);
                    case PUBLISH:
                        onPublish(ctx, (MqttPublishMessage) msg);
                        return;
                    case PUBACK:
                        onPuback(ctx, (MqttPubAckMessage)msg);
                        return;
                    case PUBREC:
                        onPubrec(ctx, msg);
                        return;
                    case PUBREL:
                        onPubrel(ctx, msg);
                        return;
                    case PUBCOMP:
                        onPubcomp(ctx, msg);
                        return;
                    case SUBACK:
                        onSuback(ctx, (MqttSubAckMessage)msg);
                        return;
                    case SUBSCRIBE:
                        onSubscribe(ctx, (MqttSubscribeMessage) msg);
                        return;
                    case UNSUBSCRIBE:
                        onUnsubscribe(ctx, (MqttUnsubscribeMessage) msg);
                        return;
                    case UNSUBACK:
                        onUnsuback(ctx, (MqttUnsubAckMessage) msg);
                        return;
                    case PINGREQ:
                        onPingreq(ctx, msg);
                        return;
                    case PINGRESP:
                        onPingresq(ctx, msg);
                        return;
                    case DISCONNECT:
                        onDisconnect(ctx);
                        return;
                    default:
                        return;
                }
            }
        }catch (Exception e){
            System.out.println("传输过来的报文不符合协议格式");
        }
    }

    /**
     * @description 处理客户端的连接请求
     * @param ctx
     * @param msg
     */
    private void onConnect(ChannelHandlerContext ctx, MqttConnectMessage msg)
    {
        //连接确认包括固定头与连接确认可变头，连接确认可变头包括返回码与会话标志
        MqttConnAckVariableHeader variableheader = new MqttConnAckVariableHeader(
                MqttConnectReturnCode.CONNECTION_ACCEPTED, false);
        MqttFixedHeader CONNACK_HEADER = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
        MqttConnAckMessage connAckMessage = new MqttConnAckMessage(CONNACK_HEADER, variableheader);
        //ctx.write(MQEncoder.doEncode(ctx.alloc(),connAckMessage));
        ctx.write(connAckMessage);
        this.clientId = msg.payload().clientIdentifier();
        this.cleanSession = msg.variableHeader().isCleanSession();
        if(msg.variableHeader().keepAliveTimeSeconds() > 0 && msg.variableHeader().keepAliveTimeSeconds() <= keepAliveMax){
            this.keepAlive = msg.variableHeader().keepAliveTimeSeconds();
        }
        this.userName = msg.payload().userName();
        this.password = msg.payload().password();
        boolean userNameFlag = msg.variableHeader().hasUserName();
        boolean passwordFlag = msg.variableHeader().hasPassword();
        boolean flag = false;
        if (userNameFlag) {
            if (this.userName == null || this.userName == "")
                flag = true;
        } else {
            if (this.userName != null && this.userName != "" || passwordFlag) flag = true;
        }
        if (passwordFlag) {
            if (this.password == null || this.password == "") flag = true;
        } else {
            if (this.password != null && this.password != "") flag = true;
        }
        if(flag == false){
            System.out.println("用户名及密码正确");
        }

    }

    /**
     * @description 连接确认
     * @param ctx
     * @param msg
     */
    private void onConnack(ChannelHandlerContext ctx, MqttConnAckMessage msg) {

    }

    /**
     * @description 发布消息
     * @param ctx
     * @param msg
     */
    private void onPublish(ChannelHandlerContext ctx, MqttPublishMessage msg) {

    }

    /**
     * @description 发布确认
     * @param ctx
     * @param msg
     */
    private void onPuback(ChannelHandlerContext ctx, MqttPubAckMessage msg) {

    }

    /**
     * @description 发布收到
     * @param ctx
     * @param msg
     */
    private void onPubrec(ChannelHandlerContext ctx, MqttMessage msg) {

    }

    /**
     * @description 发布释放
     * @param ctx
     * @param msg
     */
    private void onPubrel(ChannelHandlerContext ctx, MqttMessage msg) {

    }

    /**
     *
     * @param ctx
     * @param msg
     */
    private void onPubcomp(ChannelHandlerContext ctx, MqttMessage msg) {

    }

    /**
     * @description 订阅确认
     * @param ctx
     * @param msg
     */
    private void onSuback(ChannelHandlerContext ctx, MqttSubAckMessage msg) {

    }

    /**
     * @description 订阅
     * @param ctx
     * @param msg
     */
    private void onSubscribe(ChannelHandlerContext ctx, MqttSubscribeMessage msg) {

    }

    /**
     * @description 取消订阅
     * @param ctx
     * @param msg
     */
    private void onUnsubscribe(ChannelHandlerContext ctx, MqttUnsubscribeMessage msg) {

    }

    /**
     * @description 取消订阅确认
     * @param ctx
     * @param msg
     */
    private void onUnsuback(ChannelHandlerContext ctx, MqttUnsubAckMessage msg) {

    }

    /**
     * @description ping请求
     * @param ctx
     * @param msg
     */
    private void onPingreq(ChannelHandlerContext ctx, MqttMessage msg) {

    }

    /**
     * @description ping 响应
     * @param ctx
     * @param msg
     */
    private void onPingresq(ChannelHandlerContext ctx, MqttMessage msg) {

    }

    /**
     * @description 断开连接
     * @param ctx
     */
    private void onDisconnect(ChannelHandlerContext ctx) {

    }

    /**
     * @description
     * @param args
     */
    public static void main(String[] args)
    {
        /*
        String msg = "{\"deviceNum\":\"88888888\",\"jumpFlag\":0,\"msgId\":\"M20170829153611748025\",\"status\":1}";
        StbReportMsg stbmsg= GsonJsonUtil.fromJson(msg, StbReportMsg.class);
        System.out.println(stbmsg.getMsgType());
        */
    }
}

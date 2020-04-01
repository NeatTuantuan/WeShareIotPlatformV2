package com.bdi.mqtt.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @description mqtt协议客户端(订阅端)这个先放着
 * @date 20200401
 * @author 
 */
public class MqttClient {

    private static final int MAX_RETRY = 5;//最大重连次数
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8234;
   

    public static void main(String[] args){

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                     .channel(NioSocketChannel.class)
                     .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                     .option(ChannelOption.SO_KEEPALIVE, true)//心跳保活
                     .option(ChannelOption.TCP_NODELAY, true)
                     .handler(new ChannelInitializer<SocketChannel>() {

                         @Override
                         public void initChannel(SocketChannel ch){
//                             ch.pipeline().addLast(new MqttClientHandler());
                         }
                     });
            connect(bootstrap, HOST, PORT, MAX_RETRY);
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void connect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(host, port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("客户端连接成功");
            }else if (retry == 0){
                System.err.println("重试次数已用完，放弃连接");
            }else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.out.println(new Date() + "：连接失败，第" + order + "次重连...");
                bootstrap.group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }
}

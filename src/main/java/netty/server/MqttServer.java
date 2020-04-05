package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import netty.handler.MqttServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * @description mqtt协议服务端(代理端)
 * @date 20200401
 * @author
 */
public class MqttServer {

    public static void main(String[] args) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch) throws Exception{

                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IdleStateHandler(10, 10, 120, TimeUnit.SECONDS));//心跳机制
//                            p.addLast(MqttEncoder.INSTANCE);//编码
                            p.addLast(new MqttDecoder());//解码
                            p.addLast(new MqttServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)//等待队列
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR,PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(1883);
            b.bind("0.0.0.0", 1883).sync().channel();
            f.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}


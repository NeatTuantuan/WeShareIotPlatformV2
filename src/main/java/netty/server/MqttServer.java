package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import netty.handler.*;

import java.util.concurrent.TimeUnit;

/**
 * @description mqtt协议服务端(代理端)
 * @date 20200401
 * @author
 */
public class MqttServer {
    public static void main(String[] args) {
        int port = 1883;
        try {
            new MqttServer().bind(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void bind(int port)throws Exception{
        /**
         * bossGroup只处理连接请求，真正的客户端业务处理会交给workerGroup
         * 两个都是无限循环
         * bossGroup和workerGroup含有的子线程数（NioEventLoop）默认CPU核数*2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,1024)//设置线程队列得到链接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动链接的状态
                    .childHandler(new ChildChannelHandler());

            //调用 bind 方法绑定监听端口，调用 sync 方法同步等待绑定操作完成
            ChannelFuture f = b.bind(port).sync();

            System.out.println(Thread.currentThread().getName() + ",服务器开始监听端口，等待客户端连接.........");
            //对关闭通道进行监听
            f.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    //创建一个通道测试对象）
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
        //给pipeline设置处理器
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            //ShellHandler为自定义处理类
            socketChannel.pipeline().addLast(new MqttDecoder());
            socketChannel.pipeline().addLast(MqttEncoder.INSTANCE);
            socketChannel.pipeline().addLast(new MqttServerHandler());
            socketChannel.pipeline().addLast(new HbaseHandler());
//            socketChannel.pipeline().addLast(new StringDecoder());
//            socketChannel.pipeline().addLast(new EncapsulationHandler());
//            socketChannel.pipeline().addLast(new ShellHandler());
//            socketChannel.pipeline().addLast(new ProducerHandler());

        }
    }
}


package com.mwk.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;


/**
 * Netty服务   服务启动监听器
 * 服务启动后，可使用网络调试助手软件进行TCP连接调试
 */
@Slf4j
@Component
public class NettyServer {

    @Value("${netty.port}")
    private int port;

    public static ServerSocketChannel serverSocketChannel;
    @Autowired
    private ServerHandler serverHandler;


    public void start() throws Exception {
        // 连接处理group
        EventLoopGroup boss = new NioEventLoopGroup();
        // 事件处理group
        EventLoopGroup worker = new NioEventLoopGroup();

        //1.创建ServerBootStrap实例
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 绑定处理group
        //2.设置并绑定Reactor线程池：EventLoopGroup，EventLoop就是处理所有注册到本线程的Selector上面的Channel
        bootstrap.group(boss, worker)
                //3.设置并绑定服务端的channel
                .channel(NioServerSocketChannel.class)
                // 保持连接数
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 有数据立即发送
                .option(ChannelOption.TCP_NODELAY, true)
                // 保持连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 处理新连接
                //设置了客户端连接socket属性。
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        // 增加任务处理
                        ChannelPipeline p = sc.pipeline();
                        p.addLast(// 自定义解码器
                                // 自己进行解码，避免netty自动解码导致乱码
                                new MessagePacketDecoder(),
                                //默认的编码器
                                new StringEncoder(Charset.forName("utf-8")),
                                new StringDecoder(Charset.forName("utf-8")),
                                // 自定义的处理器
                                // new ServerHandler()
                                serverHandler);
                    }
                });

        // 绑定端口，同步等待成功
        ChannelFuture future;
        try {
            log.info("netty服务器在[{}]端口启动监听", port);
            //真正让netty跑起来的重点
            future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                serverSocketChannel = (ServerSocketChannel) future.channel();
                log.info("netty服务开启成功");
            } else {
                log.info("netty服务开启失败");
            }
            // 等待服务监听端口关闭,就是由于这里会将线程阻塞，导致无法发送信息，所以我这里开了线程
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅地退出，释放线程池资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}


package com.mwk.netty;


import com.mwk.utils.HexUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 自定义服务端处理器
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 在与客户端的连接已经建立之后将被调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("netty客户端与服务端连接开始...");
    }

    /**
     * 当从客户端接收到一个消息时被调用
     * msg 就是硬件传送过来的数据信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("连接地址：{} 消息体：{}", ctx.channel().remoteAddress(), msg.toString());
        String decodeMsg = "";
        try {
            decodeMsg = HexUtil.hexToGB18130(msg.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("解码消息：{}", decodeMsg);
    }


    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("netty客户端与服务端连接关闭...");
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        log.info("信息接收完毕...");
    }

    /**
     * 在处理过程中引发异常时被调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        log.error("异常信息 ", cause);
    }

}
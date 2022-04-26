package com.mwk.netty;

import com.mwk.utils.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义解码器
 * netty中使用Buffer接收数据而buffer默认使用ByteBuffer进行接收数据，
 * byteBuffer解码超出了byte的存储数值范围（byte数据容量应该都知道）最终导致乱码
 *
 * @author MinWeikai
 * @date 2022-04-26 18:27:55
 */
public class MessagePacketDecoder extends ByteToMessageDecoder {


    public MessagePacketDecoder() throws Exception {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        try {
            if (buffer.readableBytes() > 0) {
                // 待处理的消息包
                byte[] arr = new byte[buffer.readableBytes()];
                buffer.readBytes(arr, 0, arr.length);
                // 将字节数据转为十六进制
                out.add(HexUtil.bytes2Hex(arr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

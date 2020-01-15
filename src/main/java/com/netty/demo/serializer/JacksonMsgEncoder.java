package com.netty.demo.serializer;

import com.netty.demo.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 采用jackson对对象进行序列化传输，传输格式为:消息头+消息体
 * 消息头:数据长度
 * 消息体:数据
 *
 * @param <T>
 */
public class JacksonMsgEncoder extends MessageToByteEncoder {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object t, ByteBuf byteBuf) throws Exception {
        byte[] body = JsonUtils.toJson(t).getBytes();
        int dataLength = body.length;
        byteBuf.writeInt(dataLength);
        byteBuf.writeBytes(body);
    }
}

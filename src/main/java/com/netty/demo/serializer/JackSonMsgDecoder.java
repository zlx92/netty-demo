package com.netty.demo.serializer;

import com.netty.demo.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class JackSonMsgDecoder<T> extends ByteToMessageDecoder {
    public static int HEAD_LENGTH = 4;//long是8个字节
    private T target = null;

    public JackSonMsgDecoder(T obj) {
        this.target = obj;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < HEAD_LENGTH) {
            return;
        }

        byteBuf.markReaderIndex();//标记当前位置，便于后面回滚
        int dataLength = byteBuf.readInt();
        //数据长度不该为0
        if (dataLength < 0) {
            channelHandlerContext.close();
        }

        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();//配合前面保存的位置回滚
            return;
        }

        byte[] body = new byte[dataLength];
        byteBuf.readBytes(body);
        list.add(JsonUtils.parse(new String(body), target.getClass()));
    }
}

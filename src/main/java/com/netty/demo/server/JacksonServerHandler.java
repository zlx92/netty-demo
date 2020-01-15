package com.netty.demo.server;

import com.netty.demo.callback.IReceiveMsgCallBack;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class JacksonServerHandler extends ChannelInboundHandlerAdapter {
    private IReceiveMsgCallBack receiveMsg;

    public JacksonServerHandler(IReceiveMsgCallBack receiveMsg) {
        this.receiveMsg = receiveMsg;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        receiveMsg.receiveMsg(msg);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

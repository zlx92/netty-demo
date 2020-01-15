package com.netty.demo.server;

import com.netty.demo.callback.IReceiveMsgCallBack;
import com.netty.demo.serializer.JackSonMsgDecoder;
import com.netty.demo.serializer.JacksonMsgEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class JacksonTransferServer<T> {
    private final int port;
    private T target;
    private IReceiveMsgCallBack receiveMsgCallBack;

    public JacksonTransferServer(int port, T obj, IReceiveMsgCallBack receiveMsgCallBack) {
        this.port = port;
        this.target = obj;
        this.receiveMsgCallBack = receiveMsgCallBack;
    }


    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new JacksonMsgEncoder(),
                                    new JackSonMsgDecoder<>(target), new JacksonServerHandler(receiveMsgCallBack));
                        }
                    });
          b.bind(port).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

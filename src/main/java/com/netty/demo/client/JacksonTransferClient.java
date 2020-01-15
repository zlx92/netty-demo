package com.netty.demo.client;


import com.netty.demo.serializer.JackSonMsgDecoder;
import com.netty.demo.serializer.JacksonMsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class JacksonTransferClient<T> {
    private T target;

    private int port;

    private String host;

    public JacksonTransferClient(String host, int port, T obj) {
        this.host = host;
        this.port = port;
        this.target = obj;
    }

    public void send() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new JacksonMsgEncoder(),
                                    new JackSonMsgDecoder<>(target), new JacksonClientHandler<>(target)
                            );
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {

        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}

package com.netty.inandout;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(work).channel(NioSocketChannel.class)
                    .handler(new CInitializer());

            ChannelFuture future = bootstrap.connect("127.0.0.1", 7000).sync();
            future.channel().closeFuture();

        } finally {
            work.shutdownGracefully();
        }
    }
}

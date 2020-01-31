package com.netty.inandout;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work).channel(NioServerSocketChannel.class)
                    .childHandler(new Initializer());

            ChannelFuture future = bootstrap.bind(7000).sync();
            ChannelFuture sync = future.channel().closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}

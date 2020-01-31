package com.netty.websoclet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Myserver {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,work).handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        //是以块写
                        pipeline.addLast(new ChunkedWriteHandler());
                        //就是将多个段聚合起来
                        //当浏览器发送大量数据时，就会发出多个http请求
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        /**
                         * 对于websocket 他是帧方式传递
                         * WebSocketServerProtocolHandler是将http协议升级为ws协议
                         * 浏览器请求时ws://localhost:7000/hello
                         * WebSocketFrame
                         */
                        pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                        pipeline.addLast(new MyHandler01());

                    }
                });

        ChannelFuture future = bootstrap.bind(7000).sync();
        ChannelFuture channelFuture = future.channel().closeFuture().sync();

        boss.shutdownGracefully();
        work.shutdownGracefully();
    }
}

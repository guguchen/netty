package com.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 创建两个线程组
         * boss只处理客户端连接请求，业务处理交给work
         * 无限循环
         * 默认子线程数为CPU核心*2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //服务器端的启动对象，配置参数
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)//线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {


                            ch.pipeline().addLast("decoder",new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("------服务器准备好了------");

            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess())
                        System.out.println("绑定端口成功");
                    else
                        System.out.println("绑定失败");
                }
            });
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }



    }
}

package com.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {
    private int port;
    private String host;

    public GroupChatClient(String host,int port){
        this.port=port;
        this.host=host;
    }
    public void run(){
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {

        Bootstrap bootstrap = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder",new StringDecoder());
                        pipeline.addLast("encoder",new StringEncoder());
                        pipeline.addLast(new MyHandler2());
                    }
                });

            ChannelFuture connect = bootstrap.connect(host, port).sync();

            System.out.println("-------"+connect.channel().localAddress()+"------");
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String msg = scanner.nextLine();
                connect.channel().writeAndFlush(msg+"\r\n");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();

        }



    }
    public static void main(String[] args) {
        new GroupChatClient("127.0.0.1",7000).run();
    }
}

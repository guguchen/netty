package com.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * 自定义一个Handler需要netty规定好的handlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据
     * @param ctx 上下文对象，含有管道，通道，地址
     * @param msg 客户端发送的信息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //阻塞太久，如何解决
        /**
         * 用户自定义的普通任务
         * 用户自定义定时任务
         */
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端2",CharsetUtil.UTF_8));


            }
        });

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端3",CharsetUtil.UTF_8));
            }
        },5, TimeUnit.SECONDS);
        System.out.println("go on....");


      /*  System.out.println("服务器线程信息 "+Thread.currentThread().getName());
        System.out.println("server ctx "+ctx);
        System.out.println("看看Channel和pipeline的关系");

        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();

        //为netty提供的，非NIO，性能更高
        ByteBuf buf=(ByteBuf)msg;
        System.out.println("客户端的信息 "+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址 "+ctx.channel().remoteAddress());*/

    }
    //向客户端回写
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端",CharsetUtil.UTF_8));
    }
    //发生异常关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
         ctx.close();
    }
}

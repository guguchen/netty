package com.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

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
        StudentPOJO.Student student= (StudentPOJO.Student) msg;
        System.out.println("客户端发送的数据"+student.toString());





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

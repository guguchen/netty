package com.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    //当通道就绪就会触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("bzt").build();
        ctx.writeAndFlush(student);


    }
    //当通道有数据时可以触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        System.out.println("服务器回复的 "+buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址 "+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

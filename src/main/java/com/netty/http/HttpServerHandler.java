package com.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.channels.Pipe;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断httpobject是不是一个请求
        if(msg instanceof HttpRequest){
            System.out.println("pipeline hashcode "+ctx.pipeline().hashCode());
            System.out.println("msg 类型"+msg.getClass());
            System.out.println(ctx.channel().remoteAddress());

            HttpRequest httpRequest=(HttpRequest)msg;
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了图标，不做响应");
                return;
            }



            //回复信息给浏览器
            ByteBuf buf= Unpooled.copiedBuffer("hello 我是服务器", CharsetUtil.UTF_16);//win10下面如果用UTF_8会出现乱码
            //构造一个http响应
           FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");

            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());

            ctx.writeAndFlush(response);
        }

    }
}

package com.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class Handler01 extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        if(evt instanceof IdleStateEvent){
           IdleStateEvent event=(IdleStateEvent) evt;
           String eventType=null;

           switch (event.state()){
               case READER_IDLE:
                   eventType="读空闲";
                   break;
               case ALL_IDLE:
                   eventType="读写空闲";
                   break;
               case WRITER_IDLE:
                   eventType="写空闲";
                   break;

           }

            System.out.println(ctx.channel().remoteAddress()+"-----超时时间-----"+eventType);
            System.out.println("服务器继续做");
            //ctx.channel().close();
        }
    }
}

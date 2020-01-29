package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open();

         open.configureBlocking(false);

         if(!open.connect(new InetSocketAddress("127.0.0.1",6666))){
             while(!open.finishConnect()){
                 System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作");
             }
         }
         String str="hello world";

        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

        open.write(byteBuffer);
        System.in.read();

    }
}

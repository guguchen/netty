package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGathering {
    public static void main(String[] args) throws IOException {
        //使用ServerSocketChannel和SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7777);
        //绑定端口
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]= ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true){
            int read=0;
            while(read<8){
                long read1 = socketChannel.read(byteBuffers);
                read+=read1;
                System.out.println(read1);
                Arrays.asList(byteBuffers).stream().map(buffer -> buffer.position()+","+buffer.limit()).forEach(
                        System.out::println
                );
            }
            Arrays.asList(byteBuffers).stream().forEach(buffer->buffer.flip());
            long write=0;
            while(write<8){
                long l = socketChannel.write(byteBuffers);
                write+=l;
            }
            Arrays.asList(byteBuffers).stream().forEach(buffer->buffer.clear());


        }

    }
}

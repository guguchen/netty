package com.netty.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChnnal1 {
    public static void main(String[] args) throws IOException {
        String str="hello world";
        //如果使用FileInputStream会抛出Exception in thread "main" java.nio.channels.NonWritableChannelExcep.tion
        RandomAccessFile file = new RandomAccessFile("D:\\file01.txt","rw");
        FileChannel fileChannel=file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        file.close();


    }
}

package com.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannel2 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\file01.txt"));
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        fileInputStreamChannel.read(allocate);
        System.out.println(new String(allocate.array()));
        fileInputStream.close();


    }
}

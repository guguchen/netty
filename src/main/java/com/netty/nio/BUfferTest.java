package com.netty.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channel;

public class BUfferTest {
    public static void main(String[] args) {
        //没有Direct方法？
        IntBuffer intBuffer=IntBuffer.allocate(5);
        intBuffer.put(new int[]{121,1,5,9,7});
        intBuffer.flip();

        while(intBuffer.hasRemaining())
            System.out.println(intBuffer.get());

        ByteBuffer byteBuffer=ByteBuffer.allocateDirect(10);

        byteBuffer.asReadOnlyBuffer();
        byteBuffer.put((byte) 'h');
        System.out.println(byteBuffer.isReadOnly());
        System.out.println(byteBuffer.isDirect());
    }
}

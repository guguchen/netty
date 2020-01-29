package com.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        /**
         * 线程池机制改善BIO
         * 1、创建线程池
         * 2、如果有客户端连接，则创建一个线程处理通信
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("server open...");
        while(true){
            final Socket socket=serverSocket.accept();
            System.out.println("a client connect...");
            executorService.execute(new Runnable() {
                public void run() {

                    handler(socket);
                }
            });

        }
    }

    /**
     * 与客户端通信方法
     * @param socket
     */
    public static void handler(Socket socket){
        try{
            System.out.println("线程信息"+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true){
                int read = inputStream.read(bytes);
                if(read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            System.out.println("connnect close");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

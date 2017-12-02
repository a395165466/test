import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhangguoqing.zgq on 2017/2/14.
 * 测试NIO ServerSocketChannel
 */
public class Server {
    public static void main(String[] args) {
        try {
            demo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void demo() throws IOException {

        //serverSocketChannel1
        //这里的serverSocketChannel相当于一个webServer，监听某一个端口
        ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
        serverSocketChannel1.socket().bind(new InetSocketAddress("localhost", 12017));
        serverSocketChannel1.configureBlocking(false);

        //负责读取数据的socket
        SocketChannel socket = SocketChannel.open();
        socket.configureBlocking(false);

        ByteBuffer readBuffer = ByteBuffer.allocate(10);
        ByteBuffer writeBuffer;

        //待下载的文件
        RandomAccessFile accessFile = new RandomAccessFile(new File("D:/idea_project/test-data/ATA.png"), "r");
        accessFile.seek(0);

        Selector selector = Selector.open();
        serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);

        byte[] readTmp = new byte[10];
        byte[] writeTmp = new byte[100];
        while (true) {
            int channelReadCount = selector.select();
            if (channelReadCount == 0) {
                continue;
            }

            int count = 0;
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {

                    //新accept的连接加入到selctor中，不必再新建线程单独处理
                    //这样就节省了线程，一个线程就可以监听多个channel
                    socket = serverSocketChannel1.accept();
                    socket.configureBlocking(false);
                    long offset = 0;
                    SelectionKey regKey = socket.register(selector, SelectionKey.OP_WRITE);
                    regKey.attach(offset);

                    //向客户端发消息
//                    socket.write(ByteBuffer.wrap(new String("send message to client").getBytes()));
                } else if (key.isReadable()) {
                    System.out.println("read!");
                    //哪个channel有了读事件，哪个channel负责读
                    socket = (SocketChannel) key.channel();

                    //连续读
                    while ((count = socket.read(readBuffer)) != 0) {
                        readBuffer.flip();

                        readBuffer.get(readTmp, 0, count);

                        System.out.print(new String(readTmp));
                        readBuffer.clear();
                    }

                } else if (key.isWritable()) {
                    System.out.println("write!");
                    //哪个channel有了写事件，哪个channel负责写
                    socket = (SocketChannel) key.channel();
                    long off = (Long) key.attachment();

                    //并发操作
                    synchronized (accessFile) {
                        accessFile.seek(off);
                        int readLen = accessFile.read(writeTmp);
                        if (readLen == -1) {
                            //关闭socket
                            key.cancel();
                            socket.close();
                            System.out.println("close!");
                        } else {
                            writeBuffer = ByteBuffer.wrap(writeTmp, 0, readLen);
                            while (writeBuffer.hasRemaining()) {
                                socket.write(writeBuffer);
                            }

                            writeBuffer.clear();
                            key.attach(readLen+off);
                        }
                    }
                }

                iterator.remove();
            }
        }
    }
}
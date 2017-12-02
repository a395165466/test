import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.nio.channels.Selector;

/**
 * Created by zhangguoqing.zgq on 2017/2/8.
 * java NIO client 连接到Server并与之通信
 * client运用多线程的形式下载文件
 *
 */
public class Client {
    public static void main(String[] args) {
        demo();
    }

    public static void demo() {
        //客户端多线程连接服务端
        ExecutorService executor = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 30; i++) {
            ClientThread clientThread = new ClientThread(i);
            executor.execute(clientThread);
        }
    }
}

class ClientThread extends Thread {
    private Selector selector;
    private SocketChannel socket;
    private int threadNum;
    private File destFile;
    private FileOutputStream outputStream;
    public ClientThread(int i) {
        threadNum = i;
        try {
            selector = Selector.open();
            destFile = new File("D:/idea_project/test-data-copy/ATA" + threadNum + ".png");
            if (!destFile.exists()) {
                destFile.createNewFile();
            }

            outputStream = new FileOutputStream(destFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        //通用的socket
        SocketChannel commonSocket;
        try {
            commonSocket = SocketChannel.open();
            commonSocket.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str = "I am thread " + threadNum + "!";
        ByteBuffer readBuffer = ByteBuffer.allocate(100);
        ByteBuffer writeBuffer = ByteBuffer.wrap(str.getBytes());

        try {
            socket = SocketChannel.open();
            socket.configureBlocking(false);

            //一般是在要写数据的时候才会监听可写事件，否则会非常耗费CPU
            socket.register(selector, SelectionKey.OP_CONNECT);
            socket.connect(new InetSocketAddress("localhost", 12017));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int count = 0;
        int readLen;
        byte[] tmp = new byte[100];
        SelectionKey key;
        while (true) {
            try {
                count = selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (count == 0) {
                continue;
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()) {
                key = iterator.next();
                //获取socket
                commonSocket = (SocketChannel) key.channel();
                if (key.isConnectable()) {
                    System.out.println("thread " + threadNum + " connect!");
                    //判断connect状态
                    if (commonSocket.isConnectionPending()) {
                        //完成TCP连接的建立（TCP三次握手）
                        try {
                            commonSocket.finishConnect();

                            commonSocket.configureBlocking(false);
                            commonSocket.register(selector, SelectionKey.OP_READ);

                            commonSocket.write(writeBuffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (key.isWritable()) {//可写
                    System.out.println("thread " + threadNum + " write!");

                } else if (key.isReadable()) {//可读
                    try {
                        readLen = commonSocket.read(readBuffer);
                        System.out.println("thread " + threadNum + " read " + readLen + "!");
                        if (readLen > 0) {
                            //写入到文件
                            readBuffer.flip();
                            readBuffer.get(tmp, 0, readLen);
                            outputStream.write(tmp, 0, readLen);
                            readBuffer.clear();
                        } else {
                            key.cancel();
                            commonSocket.close();
                            outputStream.close();
                            System.out.println("thread " + threadNum + " close!");
                        }
                    } catch (IOException e) {
                        //IOException关闭连接
                        key.cancel();
                        try {
                            commonSocket.close();
                            outputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        System.out.println("thread " + threadNum + " close!");
                    }
                }
                iterator.remove();
            }
        }
    }
}
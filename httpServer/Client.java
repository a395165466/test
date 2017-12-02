import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangguoqing.zgq on 2017/2/8.
 * java NIO client 连接到Server并与之通信
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

    public ClientThread(int i) {
        threadNum = i;
        try {
            selector = Selector.open();
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

        ByteBuffer readBuffer = ByteBuffer.allocate(100);
        try {
            socket = SocketChannel.open();
            socket.configureBlocking(false);

            //一般是在要写数据的时候才会监听可写事件，否则会非常耗费CPU
            socket.register(selector, SelectionKey.OP_CONNECT);
            socket.connect(new InetSocketAddress(9999));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int count = 0;
        int readLen;
        while (true) {
            try {
                count = selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (count == 0) {
                continue;
            }

            SelectionKey key;
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()) {
                key = iterator.next();
                commonSocket = (SocketChannel) key.channel();
                if (key.isConnectable()) {
                    System.out.println("thread " + threadNum + " connect!");
                    //判断connect状态
                    if (commonSocket.isConnectionPending()) {
                        //完成TCP连接的建立（TCP三次握手）
                        try {
                            commonSocket.finishConnect();

                            commonSocket.configureBlocking(false);
                            commonSocket.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (key.isWritable()) {//可写
                    System.out.println("thread " + threadNum + " write!");

                    String str = "Client"  + threadNum + ": get request!";
                    ByteBuffer writeBuffer = ByteBuffer.wrap(str.getBytes());

                    try {
                        while (writeBuffer.hasRemaining()) {
                            commonSocket.write(writeBuffer);
                        }
                        key.interestOps(key.interestOps() &~ SelectionKey.OP_WRITE);//取消写事件

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (key.isReadable()) {//只是表示是否可读，不代表一定会有数据
                    try {
                        readLen = commonSocket.read(readBuffer);
                        if (readLen > 0) {
                            readBuffer.flip();
                            System.out.println(new String(readBuffer.array()));
                            readBuffer.clear();
                        } else {
                            key.cancel();
                            commonSocket.close();
                            System.out.println("thread " + threadNum + " close!");
                        }
                    } catch (IOException e) {
                        key.cancel();//取消该key对应的channel到select的注册
                        try {
                            commonSocket.close();
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
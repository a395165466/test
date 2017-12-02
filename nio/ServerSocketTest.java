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
public class ServerSocketTest {
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
        SocketChannel socketReadChannel = SocketChannel.open();
        socketReadChannel.configureBlocking(false);
        ByteBuffer readBuffer = ByteBuffer.allocate(100);

        //负责写数据的socket
        SocketChannel socketWriteChannel = SocketChannel.open();
        socketWriteChannel.configureBlocking(false);
        ByteBuffer writeBuffer = ByteBuffer.allocate(100);

        Selector selector = Selector.open();
        serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int channelReadCount = selector.select();
            if (channelReadCount == 0) {
                continue;
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {

                    //新accept的连接加入到selctor中，不必再新建线程单独处理
                    //这样就节省了线程，一个线程就可以监听多个channel
                    SocketChannel socket = serverSocketChannel1.accept();
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);

                    System.out.println("accept！");
                } else if (key.isReadable()) {
                    System.out.println("read!");
                    //哪个channel有了读事件，哪个channel负责读
                    //将读取到的数据输出
                    socketReadChannel = (SocketChannel) key.channel();
                    int count = 0;
                    byte[] byteArr;
                    while ((count = socketReadChannel.read(readBuffer)) != -1) {
                        byteArr = readBuffer.array();
                        System.out.print(new String(byteArr));
                    }
                } else if (key.isWritable()) {
                    System.out.println("write!");
                    //哪个channel有了写事件，哪个channel负责写
                    //所谓的写事件是指什么？
                    socketWriteChannel = (SocketChannel) key.channel();

                    while (writeBuffer.hasRemaining()) {
                        socketWriteChannel.write(writeBuffer);
                    }
                    writeBuffer.clear();
                }

                iterator.remove();
            }
        }
    }
}

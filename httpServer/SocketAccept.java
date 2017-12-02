import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;

/**
 * Created by zhangguoqing.zgq on 2017/2/27.
 */
class SocketAccept extends Thread {

    private ServerSocketChannel     server;
    private int                     port;
    private Queue<SocketChannel>    clientQueue;

    private long                    socketId;

    public SocketAccept(Queue<SocketChannel> clientQueue, int port) {
        this.clientQueue = clientQueue;
        this.port        = port;
    }

    public void run() {
        try {
            server = ServerSocketChannel.open();//ServerSocketChannel相当于一个webserver
            server.bind(new InetSocketAddress(port));

            while (true) {
                SocketChannel socketChannel = server.accept();
                socketChannel.configureBlocking(false);

                clientQueue.add(socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

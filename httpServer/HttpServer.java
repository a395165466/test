import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zhangguoqing.zgq on 2017/2/27.
 */
public class HttpServer {

    private int     clientCount;
    private Queue<SocketChannel>   clientQueue;

    private int     port;

    public HttpServer(int clientCount, int port) {
        this.clientCount  = clientCount;
        this.port         = port;
    }

    public void start() {
        this.clientQueue = new ArrayBlockingQueue<SocketChannel>(1024);//生产者-消费之模型

        Thread socketAcceptThread = new SocketAccept(clientQueue, port);
        Thread socketProcessorThread = new SocketProcessor(clientQueue);

        socketAcceptThread.start();//不断的接受client请求并放入队列
        socketProcessorThread.start();//不断的从队列取出请求并处理
    }
}

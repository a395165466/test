
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by zhangguoqing.zgq on 2017/4/16.
 */
public class MyClient {

    private long            socketId;
    private SocketChannel   socket;

    private Message         message;

    private ByteBuffer      readBuffer;
    private ByteBuffer      writeBuffer;

    public MyClient(long socketId, SocketChannel socket) {
        this.socketId       = socketId;
        this.socket         = socket;

        this.readBuffer     = ByteBuffer.allocate(1024);
//        this.writeBuffer    = ByteBuffer.allocate(1024);
        this.writeBuffer    = ByteBuffer.wrap("I have something to write!".getBytes());
    }

    public void read() throws IOException {
        int len = 0;
        while ((len = this.getSocket().read(this.getReadBuffer())) != 0) {
        }
    }

    public void write() throws IOException {
        System.out.println(new String(this.getWriteBuffer().array()));

        while (this.getWriteBuffer().hasRemaining()) {
            this.getSocket().write(this.getWriteBuffer());
        }

        this.getWriteBuffer().clear();
    }

    //处理读取的数据
    public void process() throws IOException {
        System.out.println(new String(this.getReadBuffer().array()));//输出接收到的数据

        this.getReadBuffer().flip();
        while (this.getReadBuffer().hasRemaining()) {
            this.getSocket().write(this.getReadBuffer());
        }

        this.getReadBuffer().clear();
    }

    public long getSocketId() {
        return socketId;
    }

    public void setSocketId(long socketId) {
        this.socketId = socketId;
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public void setSocket(SocketChannel socket) {
        this.socket = socket;
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public void setReadBuffer(ByteBuffer readBuffer) {
        this.readBuffer = readBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }

    public void setWriteBuffer(ByteBuffer writeBuffer) {
        this.writeBuffer = writeBuffer;
    }
}

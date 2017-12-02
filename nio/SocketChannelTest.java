import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhangguoqing.zgq on 2017/2/14.
 * 基于TCP连接的SocketChannel测试
 */
public class SocketChannelTest {
    public static void main(String[] args) {
        demo();
    }

    public static void demo() {
        File file = new File("D:/idea_project/test-data-copy/Hello.txt");
        Set<StandardOpenOption> option = new HashSet<StandardOpenOption>();
        option.add(StandardOpenOption.READ);
        option.add(StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(100);
        int count = 0;
        try {
            FileChannel fileChannel = FileChannel.open(file.toPath(), option);

            SocketAddress address = new InetSocketAddress("localhost", 12017);
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            boolean result = socketChannel.connect(address);//阻塞模式

            while (!socketChannel.finishConnect()) {

            }
            while((count = socketChannel.read(buffer)) != -1) {
                buffer.flip();

                while (buffer.hasRemaining()) {
                    fileChannel.write(buffer);
                }
                buffer.clear();
            }
            System.out.println("count: " + count);
            socketChannel.close();
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

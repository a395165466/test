import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhangguoqing.zgq on 2017/2/13.
 * 测试通过文件channel读写数据
 * Channel对Buffer的读写跟Buffer的position有关
 */
public class FileChannelTest {
    public static void main(String[] args) {
        demo3();
    }

    //transferFrom/transferTo
    public static void demo1() {
        File file1 = new File("D:/idea_project/test-data/HelloWorld.txt");
        File file2 = new File("D:/idea_project/test-data/Hello.txt");

        Set<OpenOption> options = new HashSet<OpenOption>();
        options.add(StandardOpenOption.READ);
        options.add(StandardOpenOption.WRITE);

        try {
            FileChannel fromChannel = FileChannel.open(file1.toPath(), options);
            FileChannel toChannel = FileChannel.open(file2.toPath(), options);

            long position = 0;
            long count = fromChannel.size();

            //从fromChannel写到toChannel
//            toChannel.transferFrom(fromChannel, position, count);
            fromChannel.transferTo(position, count, toChannel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void demo() {
        Set<OpenOption> options = new HashSet<OpenOption>();
        options.add(StandardOpenOption.READ);

        File file = new File("D:/idea_project/test-data/HelloWorld.txt");

        int count;
        ByteBuffer buffer = ByteBuffer.allocate(10);
//        ByteBuffer buffer1 = ByteBuffer.allocate(10);
//        buffer.compareTo(buffer1);
        char ch;
        try {
            FileChannel fileChannel = FileChannel.open(file.toPath(), options);
            while ((count = fileChannel.read(buffer)) != -1) {
                buffer.flip();//从写模式改成读模式
                while (buffer.remaining() > 0) {
                    ch = (char) buffer.get();//channel2.write(buffer);
                    System.out.print(ch);
                }

                buffer.clear();//读完之后清空缓冲区（将缓冲区置为初始状态）
            }
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void demo3() {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put("this is what i want to say...".getBytes());

        System.out.println(new String(buffer.array()));
        System.out.println(buffer.position());

        ByteBuffer writeBuffer = ByteBuffer.wrap("this is what i want to say...".getBytes());
        System.out.println(new String(writeBuffer.array()));
        System.out.println(writeBuffer.position());
    }
}

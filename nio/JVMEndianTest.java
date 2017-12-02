import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * Created by zhangguoqing.zgq on 2017/2/20.
 * 测试JVM字节序
 */
public class JVMEndianTest {
    public static void main(String[] args) {
        demo();
    }

    public static void demo() {
        int temp = 0x01020304;//16进制整型

        ByteBuffer buffer = ByteBuffer.allocate(20);
        buffer.asIntBuffer().put(temp);

        System.out.println(buffer.order());

        byte[] arr = new byte[10];


        int[] myInt = new int[] { 1, 2, 3, 4, 5, 6 };
        IntBuffer intBuffer = IntBuffer.wrap(myInt, 1, 3);

        while (intBuffer.hasRemaining()) {
            System.out.print(intBuffer.get() + " ");
        }

        System.out.println(intBuffer.capacity());

        CharBuffer b = CharBuffer.wrap("xxx");
    }
}

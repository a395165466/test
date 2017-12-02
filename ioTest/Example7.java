import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * Created by zhangguoqing.zgq on 2017/2/6.
 * 使用随机文件流类RandomAccessFile将一个文本文件倒置读出。
 */
public class Example7 {
    public static void main(String[] args) {

        demo();
    }

    public static void demo() {
        long length;
        int ch;
        StringBuffer str = new StringBuffer();
        try {
            File file = new File("D:/idea_project/test-data/Hello.txt");
            RandomAccessFile accessFile = new RandomAccessFile(file, "r");

            length = file.length();
            while (length > 0) {
                length--;
                accessFile.seek(length);

                ch = (char) accessFile.readByte();
                if (ch >= 0 && ch <= 255) {
                    str.append((char)ch);
                } else {
                    length--;
                    accessFile.seek(length);

                    byte tmpByte[] = new byte[2];
                    accessFile.readFully(tmpByte);

                    str.append(new String(tmpByte));
                }
            }

            accessFile.close();
            System.out.println(str);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void demo1() {
        String str = null;
        try {
            File file = new File("D:/idea_project/test-data/Hello.txt");
            RandomAccessFile accessFile = new RandomAccessFile(file, "r");

            //java默认使用ISO-8859-1作为其默认编码
            str = new String(accessFile.readLine().getBytes("ISO-8859-1"), "UTF-8");

            System.out.println(str);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

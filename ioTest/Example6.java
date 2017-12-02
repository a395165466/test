import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by zhangguoqing.zgq on 2017/2/6.
 * 拷贝一张图片，从一个目录到另外一个目录下
 */
public class Example6 {
    public static void main(String[] args) {

        int len = 0;
        byte[] buffer = new byte[100];
        try {
            FileInputStream inputStream = new FileInputStream(new File("D:/idea_project/test-data/ATA.png"));
            FileOutputStream outputStream = new FileOutputStream(new File("D:/idea_project/test-data/IOTest/ATA.png"));

            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer);
            }

            inputStream.close();
            outputStream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;

/**
 * Created by zhangguoqing.zgq on 2017/2/5.
 * 在程序中写一个"HelloJavaWorld你好世界"输出到操作系统文件Hello.txt文件中
 */
public class Example5 {
    public static void main(String[] args) {
        display1();
    }

    public static void display() {
        String str = "HelloJavaWorld你好世界!";
        File file = new File("D:/idea_project/test-data/Hello.txt");

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(str);
            fileWriter.flush();

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void display1() {
        String str = "HelloJavaWorld你好世界!";
        File file = new File("D:/idea_project/test-data/Hello.txt");

        try {
            FileOutputStream outputStream = new FileOutputStream(file, true);
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

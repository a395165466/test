import java.io.*;

/**
 * Created by zhangguoqing.zgq on 2017/2/4.
 * 从磁盘读取一个文件到内存中，再打印到控制台
 */
public class Example4 {
    public static void main(String[] args) {
        File file = new File("D:/idea_project/test-data/IOTest/HelloWorld.txt");
        outputFileContent(file);
    }

    public static void outputFileContent(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            System.out.println("文件不存在！");
            return;
        }

        int len;
        StringBuilder strToPrint = new StringBuilder();
        byte[] byteArr = new byte[100];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            while ((len = inputStream.read(byteArr)) != -1) {
                String str = new String(byteArr);
                strToPrint.append(str);
            }

            System.out.println(strToPrint);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

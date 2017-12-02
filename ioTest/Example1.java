import java.io.File;
import java.io.IOException;

/**
 * Created by zhangguoqing.zgq on 2017/2/4.
 *
 * 在电脑D盘下创建一个文件为HelloWorld.txt文件，判断他是文件还是目录，在创建一个目
 * 录IOTest,之后将HelloWorld.txt移动到IOTest目录下去；之后遍历IOTest这个目录下的文
 * 件
 */
public class Example1 {
    public static void main(String[] args) {
        File file = new File("D:/idea_project/test-data/HelloWorld.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (file.isFile()) {
            System.out.println("是个文件");
        } else if (file.isDirectory()) {
            System.out.println("是个目录");
        }

        File dir = new File("D:/idea_project/test-data/IOTest");
        if (!dir.exists()) {
            dir.mkdir();
        }
        //移动文件
        file.renameTo(new File("D:/idea_project/test-data/IOTest/HelloWorld.txt"));

        //遍历文件名
        for (String fileName : dir.list()) {
            System.out.println(fileName);
        }

    }
}

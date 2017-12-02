
import java.io.File;

/**
 * Created by zhangguoqing.zgq on 2017/2/4.
 * 递归实现输入任意目录，列出文件以及文件夹
 */
public class Example2 {
    public static void main(String[] args) {
        File file = new File("D:/idea_project/test-data/");
        list(file);
    }

    public static void list(File rootFile) {
//        if (rootFile == null) {
//            return;
//        }
//
//        if (!rootFile.exists()) {
//            System.out.println("文件或目录不存在");
//        }
//
//        if (rootFile.isFile()) {
//            System.out.println(rootFile.getAbsolutePath());
//        }
//
//        if (rootFile.isDirectory()) {
//            System.out.println(rootFile.getAbsolutePath());
//            for (File file : rootFile.listFiles()) {
//                if (file.isFile()) {
//                    System.out.println(file.getAbsolutePath());
//                } else if (file.isDirectory()) {
//                    list(file);
//                }
//            }
//        }

        for (File file : rootFile.listFiles()) {
            if (file.isFile()) {
                System.out.println(file.getPath());
            } else if (file.isDirectory()) {
                System.out.println(file.getPath());
                list(file);
            }
        }
    }
}

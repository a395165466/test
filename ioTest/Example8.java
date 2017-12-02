import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;

import java.io.File;
import java.security.KeyStore;
import java.util.*;

/**
 * Created by zhangguoqing.zgq on 2017/2/6.
 * 查看D盘中所有的文件和文件夹名称，并且使用名称升序降序，文件夹在前和文件夹在
 * 后，文件大小排序等。
 */
public class Example8 {
    public static List<String> files = new ArrayList<String>();

    public static void main(String[] args) {

        File file = new File("D:/idea_project/test-data/");
        list(file);

        //按照名称排序
        Collections.sort(files, new Comparator<String>() {
                @Override
                public int compare(String str1, String str2) {
                    return str1.compareTo(str2);
                }
        });

        for (String item : files) {
            System.out.println(item);
        }
    }

    public static void list(File rootFile) {
        if (rootFile == null) {
            return;
        }

        if (!rootFile.exists()) {
            System.out.println("文件或目录不存在");
            return;
        }

        if (rootFile.isFile()) {
            files.add(rootFile.getPath());
        }

        if (rootFile.isDirectory()) {
            files.add(rootFile.getPath());
            for (File file : rootFile.listFiles()) {
                if (file.isFile()) {
                    files.add(file.getPath());
                } else if (file.isDirectory()) {
                    list(file);
                }
            }
        }
    }
}

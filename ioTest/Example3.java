import java.io.File;

/**
 * Created by zhangguoqing.zgq on 2017/2/4.
 * 递归实现列出当前工程下所有.Java文件
 */
public class Example3 {
    public static void main(String[] args) {
        File file = new File("D:/idea_project/ata/");
        displayAllJavaFile(file);
    }

    public static void displayAllJavaFile(File rootFile) {
        if (rootFile == null) {
            return;
        }

        if (!rootFile.exists()) {
            System.out.println("文件或目录不存在");
            return;
        }

        if (rootFile.isFile()) {
            int len = rootFile.getName().length();
            if (len > 5 && ".java".equals(rootFile.getName().substring(len-5))) {
                System.out.println(rootFile.getPath());
            }
        }

        if (rootFile.isDirectory()) {
            for (File file : rootFile.listFiles()) {
                if (file.isFile()) {
                    int len1 = file.getName().length();
                    if (len1 > 5 && ".java".equals(file.getName().substring(len1-5))) {
                        System.out.println(file.getPath());
                    }
                } else if (file.isDirectory()) {
                    displayAllJavaFile(file);
                }
            }
        }
    }
}

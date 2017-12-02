import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangguoqing.zgq on 2017/2/6.
 * 输入两个文件夹名称，将A文件夹内容全部拷贝到B文件夹，要求使用多线程来操作
 * 实现方案是每当拷贝文件时都开启一个线程执行
 */
public class Example9 {
    private static List<File> fileList = new ArrayList<File>();
    private static String fileSrcStr = "D:/idea_project/test-data";
    private static String fileDestStr = "D:/idea_project/test-data-copy";

    public static void main(String[] args) {
        File fileSrc = new File(fileSrcStr);
        File fileDest = new File(fileDestStr);

        listAllFiles(fileSrc);

        copy();
    }

    public static void listAllFiles(File rootFile) {
        if (rootFile == null) {
            return;
        }

        if (!rootFile.exists()) {
            System.out.println("文件或目录不存在！");
            return;
        }

        if (rootFile.isFile()) {
            fileList.add(rootFile);
        }

        if (rootFile.isDirectory()) {
            fileList.add(rootFile);
            for (File file : rootFile.listFiles()) {
                if (file.isFile()) {
                    fileList.add(file);
                } else if (file.isDirectory()) {
                    listAllFiles(file);
                }
            }
        }
    }

    public static void copy() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (File fileSrc : fileList) {
            String srcName = fileSrc.getPath().replace("\\", "/");
            String destName = srcName.replace(fileSrcStr, fileDestStr);

            File file = new File(destName);

            if (fileSrc.isDirectory()) {
                file.mkdirs();
            } else if (fileSrc.isFile()) {
                //拷贝文件时使用多线程
                executorService.execute(new taskThread(fileSrc, file));
            }
        }

        executorService.shutdown();
    }
}

 class taskThread extends Thread {
     private File _fileSrc;
     private File _fileDest;

     public taskThread(File fileSrc, File fileDest) {
         _fileSrc = fileSrc;
         _fileDest = fileDest;
     }
     public void run() {
         int len = 0;
         byte[] buffer = new byte[1024];
         try {
             FileInputStream inputStream = new FileInputStream(_fileSrc);
             FileOutputStream outputStream = new FileOutputStream(_fileDest);

             while ((len = inputStream.read(buffer)) != -1) {
                 outputStream.write(buffer, 0, len);//不要忘了len,否则会补null
             }
             inputStream.close();
             outputStream.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }
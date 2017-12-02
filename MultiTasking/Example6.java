import java.io.*;

/**
 * Created by zhangguoqing.zgq on 2017/5/31.
 * 一个文件中有10000个数，用Java实现一个多线程程序将这个10000个数输出到5个不同文件中（不要求输出到每个文件中的数量相同）。
 * 要求启动10个线程，两两一组，分为5组。
 * 每组两个线程分别将文件中的奇数和偶数输出到该组对应的一个文件中，需要偶数线程每打印10个偶数以后，就将奇数线程打印10个奇数，如此交替进行。
 * 同时需要记录输出进度，每完成1000个数就在控制台中打印当前完成数量，并在所有线程结束后，在控制台打印”Done”。
 *
 http://www.itdadao.com/articles/c15a1262761p0.html
 */
public class Example6 {
    private String nextLine = "\r";
    private String numberFileName = "D:\\idea_project\\test-data\\number.txt";
    private String fileNamePre = "D:\\idea_project\\test-data\\";
    private int[] numbers = new int[10000];
    public static int number = 0;
    public static boolean[] isEvenFinished = {false, false, false, false, false};

    public void generateFile() throws IOException {
        //产生一个包含10000个数的文件
        File file = new File(numberFileName);
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file));

        for (int i = 0; i < 10000; i++) {
            outputStream.writeBytes(String.valueOf(i));
            outputStream.writeBytes(this.nextLine);
        }

        outputStream.flush();
        outputStream.close();
    }

    public void readFile() throws IOException {
        String line;
        File file = new File(numberFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while ((line = reader.readLine()) != null) {
            int number = Integer.parseInt(line);
            numbers[number] = number;
        }
        reader.close();
    }

    public void writeNumber(String filePath, int[] arr, int length) throws IOException {
        File file = new File(filePath);
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file, true));

        for (int i = 0; i < length; i++) {
            outputStream.writeBytes(String.valueOf(arr[i]));
            outputStream.writeBytes(this.nextLine);
        }

        outputStream.flush();
        outputStream.close();
    }

    //奇数
    class WriteOdd extends Thread {
        private int groupId;
        private Object lock;
        private String fileName;

        WriteOdd(int groupId, Object lock) {
            this.groupId = groupId;
            this.lock = lock;
            fileName = fileNamePre + groupId + "_odd.txt";
        }

        public void run() {
            while (isEvenFinished[groupId]) {
                //像文件中写入奇数
            }
        }
    }

    //偶数
    class WriteEven extends Thread {
        private int groupId;
        private Object lock;
        private String fileName;
        private int offset;
        private boolean finished = false;

        WriteEven(int groupId, Object lock) {
            this.groupId = groupId;
            this.lock = lock;
            fileName = fileNamePre + "group_" + groupId + "_even.txt";
        }

        public void run() {
            while(!finished) {
                synchronized (lock) {
                    //读取10个偶数写入到对应的文件中
                    int i;
                    int[] temp = new int[10];
                    String line;
                    File file = new File(numberFileName);
                    for (i = 0; i < 10; i++) {
                        if (offset < 2000) {
                            temp[i] = numbers[2000 * groupId + offset];
                            offset++;
                        } else {
                            finished = true;
                            break;
                        }
                    }

                    try {
                        writeNumber(fileName, temp, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //将groupId对应的标志位置为true，方便该组的奇数线程写数据
                    isEvenFinished[groupId] = true;

                    try {
                        Thread.sleep(100);

                        lock.notifyAll();

                        if (!finished) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Example6 example6 = new Example6();
        try {
            example6.generateFile();
            example6.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object lock = new Object();
        for (int j = 0; j < 5; j++) {
            WriteEven writeEven = example6.new WriteEven(j, lock);
//            WriteOdd  writeOdd  = example6.new WriteOdd(j, lock);

            writeEven.start();
//            writeOdd.start();
        }
    }
}


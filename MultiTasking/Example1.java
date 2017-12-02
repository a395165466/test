/**
 * Created by zhangguoqing.zgq on 2017/5/26.
 * 两个线程循环打印数字1,2,1,2,1,2
 */
public class Example1 {
    public static void main(String[] args) {
        Object lock = new Object();

//        PrintThread thread = new PrintThread(lock);
//
//        Thread thread1 = new Thread(thread, "1");
//        Thread thread2 = new Thread(thread, "2");
//
//        thread1.start();
//        thread2.start();

        PrintNumThread thread1 = new PrintNumThread(1, lock);
        PrintNumThread thread2 = new PrintNumThread(2, lock);

        thread1.start();
        thread2.start();

    }
}

class PrintThread implements Runnable {
    private Object lock;

    PrintThread(Object lock) {
        this.lock = lock;
    }

    public void run() {
        while (true) {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                    lock.notifyAll();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class PrintNumThread extends Thread {
    private int num;
    private Object lock;

    PrintNumThread(int num, Object lock) {
        this.num  = num;
        this.lock = lock;
    }

    public void run() {
        while (true) {
            synchronized (lock) {
                System.out.println(this.num);
                try {
                    Thread.sleep(1000);
                    lock.notifyAll();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
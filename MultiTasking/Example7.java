
/**
 * 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10, 然后是线程3打印11,12,13,14,15. 接着再由线程1打印16,17,18,19,20....以此类推, 直到打印到75.
 * 采用原始的synchronized, wait(), notify(), notifyAll()等方式控制线程.
 * 采用JDK1.5并发包提供的Lock, Condition等类的相关方法控制线程.
 */
public class Example7
{
    public static int times;
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        for (int i = 0; i < 3; i++) {
            PrintNumberThread thread = new PrintNumberThread(i, lock);
            thread.start();
            Thread.sleep(500);
        }
    }
}

class PrintNumberThread extends Thread {
    private int number;
    private Object lock;
    private int currentTime = 1;

    public PrintNumberThread(int number, Object lock) {
        this.number = number;
        this.lock   = lock;
    }

    public void run() {
        while (Example7.times < 15) {
            int i;
            synchronized (lock) {
                for (i = Example7.times * 5 + 1; i < (Example7.times + 1) * 5 + 1; i++) {
                    System.out.println(i);
                }

                Example7.times++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock.notifyAll();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (lock) {
            lock.notifyAll();
        }
    }
}

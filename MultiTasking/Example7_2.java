import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10
 * 然后是线程3打印11,12,13,14,15. 接着再由线程1打印16,17,18,19,20....
 * 以此类推, 直到打印到75.
 * 以一种更好的方式实现
 */
public class Example7_2 {
    public static int times;

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        Thread1 thread1 = new Thread1(lock);
        Thread2 thread2 = new Thread2(lock);
        Thread3 thread3 = new Thread3(lock);

        thread1.start();
        Thread.sleep(500);

        thread2.start();
        Thread.sleep(500);

        thread3.start();
    }
}

class Thread1 extends Thread {
    private Lock lock;

    public Thread1(Lock lock) {
        this.lock = lock;
    }

    public void run() {
        while(Example7_2.times < 13) {
            lock.lock();
            int i = 0;

            if (Example7_2.times % 3 == 0) {
                for (i = Example7.times * 5 + 1; i < (Example7_2.times + 1) * 5 + 1; i++) {
                    System.out.println(i);
                }

                Example7_2.times++;
            }

            lock.unlock();
        }
    }
}

class Thread2 extends Thread {
    private Lock lock;

    public Thread2(Lock lock) {
        this.lock = lock;
    }

    public void run() {
        while(Example7_2.times < 13) {
            lock.lock();
            int i = 0;

            if (Example7_2.times % 3 == 1) {
                for (i = Example7_2.times * 5 + 1; i < (Example7_2.times + 1) * 5 + 1; i++) {
                    System.out.println(i);
                }

                Example7_2.times++;
            }

            lock.unlock();
        }
    }
}

class Thread3 extends Thread {
    private Lock lock;

    public Thread3(Lock lock) {
        this.lock = lock;
    }

    public void run() {
        while(Example7_2.times < 13) {
            lock.lock();
            int i = 0;

            if (Example7_2.times % 3 == 2) {
                for (i = Example7_2.times * 5 + 1; i < (Example7_2.times + 1) * 5 + 1; i++) {
                    System.out.println(i);
                }

                Example7_2.times++;
            }

            lock.unlock();
        }
    }
}
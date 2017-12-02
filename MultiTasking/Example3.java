import com.kenai.jaffl.annotations.Synchronized;

/**
 * Created by zhangguoqing.zgq on 2017/5/27.
 *  编写程序实现,子线程循环1次,接着主线程循环2次
 *  接着再子线程循环1次,主线程循环2次,如此反复,循环50次.
 */
public class Example3 {
//    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 50; i++) {
//            CycleThread thread = new CycleThread();
//            thread.start();
//            thread.join();
//            cycle();
//        }
//    }


    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        CycleThread thread = new CycleThread(lock);
        thread.start();

        Thread.sleep(1000);//保证子线程先执行

        int i = 0;
        while(i++ < 50) {
            synchronized (lock) {
                cycle();

                lock.notifyAll();
                lock.wait();
            }
        }
    }

    public static void cycle() {
        for (int i = 0; i < 2; i++) {
            System.out.println("main thread cycle");
        }
    }
}

class CycleThread extends Thread {
    private Object lock;

    CycleThread(Object lock) {
        this.lock = lock;
    }

    public void run() {
        int i = 0;
        while(i++ < 50) {
            synchronized(lock) {
                for (int j = 0; j < 1; j++) {
                    System.out.println("sub thread cycle");
                }
                lock.notifyAll();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //先执行完成的线程在执行完成之后（即跳出循环之后要执行一次notify保证后面的线程都能够退成wait）
        synchronized (lock) {
            lock.notifyAll();
        }
    }
 }


import com.kenai.jaffl.annotations.Synchronized;

import javax.crypto.spec.GCMParameterSpec;

/**
 * Created by zhangguoqing.zgq on 2017/5/26.
 * 模拟多个人通过一个山洞的模拟。这个山洞每次只能通过一个人，每个人通过山洞的时间为5s一个人
 * 同时准备过此山洞，显示一下每次通过的人姓名
 */
public class Example2 {
    public static void main(String[] args) {
        Object lock = new Object();

        for(int i = 0; i < 10; i++) {
//            GoCross goCross = new GoCross(i, lock);
//            goCross.start();
            GoCave goCave = new GoCave(i, lock);
            Thread thread = new Thread(goCave);
            thread.start();
        }
    }
}

class GoCross extends Thread {
    private int num;
    private Object lock;

    GoCross(int num, Object lock) {
        this.num  = num;
        this.lock = lock;
    }

    public void run() {
        synchronized (lock) {
            System.out.println(num + " is go throughing the cave");
            try {
                Thread.sleep(5000);

                lock.notifyAll();
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class GoCave implements Runnable {
    private int num;
    private Object lock;

    public GoCave(int num, Object lock) {
        this.num = num;
        this.lock = lock;
    }

    public void run() {
        synchronized(lock) {
            System.out.println(num + " is go throughing the cave");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
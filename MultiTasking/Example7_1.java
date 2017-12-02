/**
 * 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10
 * 然后是线程3打印11,12,13,14,15. 接着再由线程1打印16,17,18,19,20....
 * 以此类推, 直到打印到75.
 * 以一种更好的方式实现
 */
public class Example7_1 {
    public static int times;
    public static void main(String[] args) throws InterruptedException {
        LockObject lockObject1 = new LockObject(1);
        LockObject lockObject2 = new LockObject(2);
        LockObject lockObject3 = new LockObject(3);

        CyclePrintNumThread thread1 = new CyclePrintNumThread(lockObject3, lockObject1);
        CyclePrintNumThread thread2 = new CyclePrintNumThread(lockObject1, lockObject2);
        CyclePrintNumThread thread3 = new CyclePrintNumThread(lockObject2, lockObject3);

        thread1.start();
        Thread.sleep(500);

        thread2.start();
        Thread.sleep(500);

        thread3.start();
    }
}

class LockObject {
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public LockObject(int num) {
        this.num = num;
    }
}

class CyclePrintNumThread extends Thread {
    private Object preLock;
    private Object selfLock;

    public CyclePrintNumThread(LockObject preLock, LockObject selfLock) {
        this.preLock   = preLock;
        this.selfLock   = selfLock;
    }

    public void run() {
        while (Example7.times < 15) {
            int i;
            synchronized (preLock) {
                synchronized (selfLock) {
                    for (i = Example7.times * 5 + 1; i < (Example7.times + 1) * 5 + 1; i++) {
                        System.out.println(i);
                    }

                    Example7.times++;
                    selfLock.notifyAll();
                }
                try {
                    preLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (selfLock) {
            selfLock.notifyAll();
        }
    }
}
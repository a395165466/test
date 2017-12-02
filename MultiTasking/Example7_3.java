package com.taobao.ata.test.MultiTasking;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangguoqing.zgq on 2017/6/2.
 * 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10
 * 然后是线程3打印11,12,13,14,15. 接着再由线程1打印16,17,18,19,20....
 * 以此类推, 直到打印到75.
 * 通过ReentrantLock和Condition方式实现
 */
public class Example7_3 {
    public static int times;

    Lock lock = new ReentrantLock();
    Condition printF = lock.newCondition();
    Condition printS = lock.newCondition();
    Condition printT = lock.newCondition();

    class ThreadPrintF extends Thread {
        public void run() {
            while(times < 15) {
                lock.lock();
                int i = 0;

                if (times % 3 == 0) {
                    for (i = times * 5 + 1; i < (times + 1) * 5 + 1; i++) {
                        System.out.println(i);
                    }

                    times++;

                    printS.signalAll();

                    try {
                        printF.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                lock.unlock();
            }

            lock.lock();
            printS.signalAll();
            lock.unlock();
        }
    }

    class ThreadPrintS extends Thread {
        public void run() {
            while(times < 15) {
                lock.lock();
                int i = 0;

                if (times % 3 == 1) {
                    for (i = times * 5 + 1; i < (times + 1) * 5 + 1; i++) {
                        System.out.println(i);
                    }

                    times++;

                    printT.signalAll();

                    try {
                        printS.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                lock.unlock();
            }

            lock.lock();
            printT.signalAll();
            lock.unlock();
        }
    }

    class ThreadPrintT extends Thread {
        public void run() {
            while(times < 15) {
                lock.lock();
                int i = 0;

                if (times % 3 == 2) {
                    for (i = times * 5 + 1; i < (times + 1) * 5 + 1; i++) {
                        System.out.println(i);
                    }

                    times++;

                    printF.signalAll();

                    try {
                        printT.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Example7_3 example7_3 = new Example7_3();

        ThreadPrintF thread1 = example7_3.new ThreadPrintF();
        ThreadPrintS thread2 = example7_3.new ThreadPrintS();
        ThreadPrintT thread3 = example7_3.new ThreadPrintT();

        thread1.start();
        Thread.sleep(500);

        thread2.start();
        Thread.sleep(500);

        thread3.start();
    }
}

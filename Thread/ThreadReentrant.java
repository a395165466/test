
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangguoqing.zgq on 2017/6/16.
 * 模拟一个生产者-消费者队列，一个线程不断的往队列里插入数据，一个线程不断的取出数据
 */
public class ThreadReentrant {
    public static void main(String[] args) {
        final BufferQueue bufferQueue = new BufferQueue();

        Thread thread1 = new Thread(new Runnable() {
            //@Override
            public void run() {
                for (int i=0;i<100;i++) {
                    int val = bufferQueue.take();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            //@Override
            public void run() {
                for (int i=0;i<100;i++) {
                    bufferQueue.put(i);
                }
            }
        });

        thread1.start();

        thread2.start();
    }
}

class BufferQueue {
    private int[] buffer = new int[10];

    private int size;

    private int index;

    ReentrantLock lock;

    Condition notFull;
    Condition notEmpty;

    public BufferQueue() {
        this.lock = new ReentrantLock();
        notFull = this.lock.newCondition();
        notEmpty = this.lock.newCondition();

        this.size = 0;
        this.index = 0;
    }

    /**
     * 往队列里插入数据
     * @param num
     */
    public void put(int num) {
        lock.lock();

        while (size >= buffer.length) {
            try {
                notFull.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            buffer[index++] = num;
            size++;
            notEmpty.signalAll();
            System.out.println("put num :" + num);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从队列里取数据
     * @return
     */
    public int take() {
        int temp;
        lock.lock();

        while (0 >= size) {
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            size--;
            temp = buffer[--index];
            System.out.println("take num :" + temp);
            notFull.signalAll();

        } finally {
            lock.unlock();
        }

        return temp;
    }
}

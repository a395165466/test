import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zhangguoqing.zgq on 2017/5/31.
 * 实现生产者和消费者
 * 生产者和消费者之间通常有一个队列来做缓存，缓存通常又使用
 * ArrayBlockingQueue实现，因为ArrayBlockingQueue本身就是
 * 并发容器，因此不需要考虑锁的因素，可以直接使用
 */
public class Example5 {
    public static void main(String[] args) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(100);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        producer.start();
        consumer.start();
    }
}

class Producer extends Thread {
    private ArrayBlockingQueue queue;

    Producer(ArrayBlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                queue.put(i);//阻塞操作
                System.out.println("put: " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private ArrayBlockingQueue queue;

    Consumer(ArrayBlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        Object object;
        while (true) {
            try {
                object = queue.take();
                System.out.println("take: " + object.toString());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

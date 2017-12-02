
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangguoqing.zgq on 2017/6/27.
 */
public class CountDownLatchExample {
    private static int ThreadNum = 10;
    public static void main(String[] args) throws InterruptedException {
        //创建一个CountDownLatch实例，进行线程并发控制
        CountDownLatch latch = new CountDownLatch(ThreadNum);

        ExecutorService executor = Executors.newFixedThreadPool(ThreadNum);


        for (int i = 0; i < ThreadNum; i++) {
            executor.execute(new Person(latch, i));
        }

        System.out.println("等待所有线程执行完成！");
        latch.await();

        executor.shutdown();

        System.out.println("所有线程已经执行完成！");
    }
}

class Person extends Thread {
    private int num;
    private CountDownLatch latch;

    public Person(CountDownLatch latch, int num) {
        this.num = num;
        this.latch = latch;
    }

    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Thread " + num + " finish!");
            latch.countDown();
        }
    }
}


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangguoqing.zgq on 2017/6/27.
 */
public class CyclicBarrierExample {
    public static final int threadNum = 10;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum, new Runnable() {
            @Override
            public void run() {
                System.out.println("go on executing!");
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(new Run(cyclicBarrier, i));
        }
    }
}

class Run extends Thread {
    private CyclicBarrier cyclicBarrier;
    private int num;

    public Run(CyclicBarrier cyclicBarrier, int num) {
        this.cyclicBarrier = cyclicBarrier;
        this.num = num;
    }

    public void run() {
        System.out.println("Thread " + num + " wait to run!");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Thread " + num + " run!");
        }
    }
}

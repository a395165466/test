
import java.util.concurrent.*;

/**
 * Created by zhangguoqing.zgq on 2017/7/31.
 * Callable&Future练习
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        //方式一
//        Task task = new Task();
//        Future<Integer> result = service.submit(task);
//
//        System.out.print("运行结果为：" + result.get());

        //方式二
        Task task = new Task();
        FutureTask<Integer> result = new FutureTask<Integer>(task);
        service.execute(result);

        System.out.print("运行结果为：" + result.get());

        service.shutdown();//不关闭的话线程池不会关闭，JVM一直运行
    }
}

class Task implements Callable<Integer> {
    public Integer call() {
        System.out.println("线程正在运行！");
        int sum = 0;

        for (int i = 0; i <= 100; i++) {
            sum += i;
        }

        return sum;
    }
}


import java.util.concurrent.Semaphore;

/**
 * Created by zhangguoqing.zgq on 2017/9/13.
 */
public class SemaphoreTest {
    private Semaphore semaphore = new Semaphore(10);

    public void add() {
        try {
            semaphore.acquire();
            System.out.println("添加数据！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void remove() {
        try {
            semaphore.acquire();
            System.out.println("删除数据！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    class ThreadAdd extends Thread {
        public void run() {
            add();
        }
    }

    class ThreadRemove extends Thread {
        public void run() {
            remove();
        }
    }

    public static void main(String[] args) {
        Thread add = new SemaphoreTest().new ThreadAdd();
        Thread remove = new SemaphoreTest().new ThreadRemove();

        add.start();
        remove.start();
    }
}

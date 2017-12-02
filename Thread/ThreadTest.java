
/**
 * Created by zhangguoqing.zgq on 2017/5/22.
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object lock = new Object();

        synchronized (lock) {
            System.out.println("begin");
            a.wait();
            System.out.println("end");
        }
    }
}

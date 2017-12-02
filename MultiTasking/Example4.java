
/**
 * Created by zhangguoqing.zgq on 2017/5/27.
 * 设计四个线程,其中两个线程每次对变量i加1,另外两个线程每次对i减1.
 * (这种情况用内部类比较合适，因为不需要在线程之间传递变量)
 * 或者通过一个锁对象实现，不同的线程在对i进行操作之前必须要
 * 先获得锁
 */
public class Example4 {
    private int i = 0;

    public synchronized void inc()
    {
        i++;
        System.out.println("i的值：" + i);
    }

    public synchronized void dec() {
        i--;
        System.out.println("i的值：" + i);
    }

    //内部类
    class AddThread extends Thread {
        public void run() {
            inc();
        }
    }

    //内部类
    class SubThread extends Thread {
        public void run() {
            dec();
        }
    }

    public static void main(String[] args) {
        Example4 example4 = new Example4();
        //多个内部类对象共享一份变量i
        for (int j = 0; j < 10; j++) {
            Thread thread = example4.new AddThread();
            thread.start();
        }

        for (int k = 0; k < 10; k++) {
            Thread thread = example4.new SubThread();
            thread.start();
        }
    }
}

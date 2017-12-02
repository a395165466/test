
/**
 * Created by zhangguoqing.zgq on 2017/6/13.
 */
public class ThreadLocalTest {
    private static ThreadLocal<String> local = new ThreadLocal<String>();
    public void setLocal (String str) {
        this.local.set(str);
    }

    public String getLocal () {
        return (String) this.local.get();
    }
    class Thread1 extends Thread {
        public void run() {
            setLocal("a");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getLocal());
        }
    }

    class Thread2 extends Thread {
        public void run() {
            setLocal("b");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getLocal());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest test = new ThreadLocalTest();
        Thread thread1 = test.new Thread1();
        Thread thread2 = test.new Thread2();

        thread1.run();

        Thread.sleep(100);

        thread2.run();
    }
}

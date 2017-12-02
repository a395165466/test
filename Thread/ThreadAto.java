
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by zhangguoqing.zgq on 2017/6/16.
 * Java并发编程Atomic实践
 */
public class ThreadAto {
    public static AtomicInteger temp = new AtomicInteger(0);
    public static volatile int normal;
    public static AtomicReference<String> reference = new AtomicReference<String>("abc");

    public static void main(String[] args) throws InterruptedException {
        ThreadAtomic1[] threads = new ThreadAtomic1[10000];
        ThreadNormal[] normalThreads = new ThreadNormal[10000];
        ThreadRefer[] referThreads = new ThreadRefer[100];

        for (int i = 0; i < 10000; i++) {
            threads[i] = new ThreadAtomic1();
            threads[i].start();
        }

        for (int i = 0; i < 10000; i++) {
            threads[i].join();
        }

        for (int j = 0; j < 10000; j++) {
            normalThreads[j] = new ThreadNormal();
            normalThreads[j].start();
        }

        for (int j = 0; j < 10000; j++) {
            normalThreads[j].join();
        }

        for (int j = 0; j < 100; j++) {
            referThreads[j] = new ThreadRefer();
            referThreads[j].start();
        }

        for (int j = 0; j < 100; j++) {
            referThreads[j].join();
        }

        System.out.println(temp.get());
        System.out.println(normal);
        System.out.println(reference);

        Map<String, String> a = new HashMap<String, String>();
    }
}

class ThreadAtomic1 extends Thread {
    public void run() {
        ThreadAto.temp.incrementAndGet();
    }
}

class ThreadNormal extends Thread {
    public void run() {
        ThreadAto.normal++;
    }
}

class ThreadRefer extends Thread {
    public void run() {
        String oldValue = ThreadAto.reference.get();
        ThreadAto.reference.compareAndSet(oldValue, oldValue + 1);
    }
}
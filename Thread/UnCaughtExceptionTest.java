
/**
 * Created by zhangguoqing.zgq on 2017/6/12.
 * UnCaughtException测试，对线程设置设置UncaughtExceptionHandler
 * 在线程存在未捕获的异常出现时捕获并处理
 */
public class UnCaughtExceptionTest {
    public static void main(String args[]) {
        UnCaughtThread thread = new UnCaughtThread();
        thread.setUncaughtExceptionHandler(new ExceptionHandler());

        thread.start();
    }
}

class UnCaughtThread extends Thread {
    public void run() {
        Integer.parseInt("ABC");
    }
}

class  ExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("程序出现异常");
        e.printStackTrace();
    }
}
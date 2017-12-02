import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理测试
 * Created by zhangguoqing.zgq on 2016/12/12.
 */
public class TestProxy implements InvocationHandler {
    private Object target;

    //生成动态代理对象
    public Object bind(Object target) {
        this.target = target;
        return  Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        Object result = null;

        if (method.getName().equals("speakChinese")) {
            System.out.println("Before speak Chinese...");
        } else if (method.getName().equals("speakEnglish")) {
            System.out.println("Before speak English...");
        } else if (method.getName().equals("speakJapanese")) {
            System.out.println("Before speak Japanese...");
        }

        result = method.invoke(target, args);

        if (method.getName().equals("speakChinese")) {
            System.out.println("After speak Chinese...");
        } else if (method.getName().equals("speakEnglish")) {
            System.out.println("After speak English...");
        } else if (method.getName().equals("speakJapanese")) {
            System.out.println("After speak Japanese...");
        }

        return result;
    }
}

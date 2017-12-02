
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhangguoqing.zgq on 2016/12/12.
 */
public class TestReflection {
    public static void main(String[] args) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException ,
            NoSuchFieldException,NoSuchMethodException,
            InvocationTargetException {
        demo2();
        demo3();
        demo4();
    }

    public static void demo2() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException
    {
        //?代表类型未知，实际的类型需要运行时才知道
        Class<?> class1 = null;
        Class<?> class2 = null;
        Class<?> class3 = null;

        class1 = Class.forName("");
        System.out.println("完整包名：" + class1.getPackage().getName() + class1.getName());

        class3 = Class.forName("Person");
        Person person = (Person) class3.newInstance();
        person.setAge(20);
        person.setName("zhang");

        System.out.println("姓名：" + person.getName() + " 年龄：" + person.getAge());
    }

    public static void demo3() throws ClassNotFoundException,
            IllegalAccessException, InstantiationException,
            NoSuchFieldException,NoSuchMethodException,
            InvocationTargetException
    {
        Class class1 = null;
        Class class2 = null;

        class1 = Class.forName("Person");

        Person person = (Person) class1.newInstance();
        Field nameFiled = class1.getDeclaredField("name");
        nameFiled.setAccessible(true);
        nameFiled.set(person, "xing");

        Method nameMethod = class1.getDeclaredMethod("setAge", Integer.class);
        nameMethod.invoke(person, 18);

        System.out.println("姓名：" + person.getName() + " 年龄：" + person.getAge());
    }

    public static void demo4()
    {
        Person person = new Person();
        person.setName("zhangguoqing");
        person.setAge(20);
        person.setSex("male");

        Set<String> test = new HashSet<String>();
        test.add("zhang");
        test.add("guoqing");
        test.add("xing");

        System.out.println(person.toString());
        System.out.println(test.toString());
    }
}

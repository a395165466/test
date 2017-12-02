
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangguoqing.zgq on 2017/2/4.
 * 测试序列化和反序列化
 * http://blog.csdn.net/wenzhi20102321/article/details/52582705(练习题)
 */
public class TestSerialize {
    public static void main(String[] args) {

        demo2();

    }

    public static void demo1() {
        Person person = new Person();
        person.setName("zhangguoqing");
        person.setSex("male");
        person.setAge(20);

        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(
                    new File("")));
            outStream.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(
                    new File("")));
            Person person1 = (Person) inputStream.readObject();
            System.out.println(person1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void demo2() {

        List<String> list = new ArrayList<String>();

        list.add("zhangguoqing");
        list.add("xiaoming");
        list.add("liuxiang");
        list.add("xiaodengzi");

        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(
                    new File("")));
            outStream.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(
                    new File("")));
            List<String> list1 = (List<String>) inputStream.readObject();
            System.out.println(list1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

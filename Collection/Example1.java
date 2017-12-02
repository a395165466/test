import java.util.*;

/**
 * Created by zhangguoqing.zgq on 2017/3/4.
 */
public class Example1 {
    public static void main(String[] args) {
        demo3();
    }

    public static void demo()
    {
        List<String> list = new ArrayList<String>();
        list.add("zhang");
        list.add("li");
        list.add("zheng");
        list.add("wang");
        System.out.println(list.toString());

        Map<String, String> map = new HashMap<String, String>();
        Set<String> set = new HashSet<String>();
        set.add("xxx");

        Set<String> treeSet = new TreeSet<String>();

        Map<String, String> treeMap = new TreeMap<String, String>();

        Iterator<String> iterator =  list.iterator();
//        iterator.next();
//        iterator.remove();

        ListIterator<String> listIterator = list.listIterator();
        listIterator.next();
        listIterator.next();
        listIterator.next();

        listIterator.add("xi");
        listIterator.add("zhao");

        listIterator.previous();
        listIterator.add("qian");//往该指针之前的元素插入
        System.out.println(list.toString());

        listIterator.previous();
        //对迭代器执行set/add/remove操作时都需要执行previous或者next操作，不能连续调用
        listIterator.set("sun");

        System.out.println(list.toString());

        System.out.println("next Index:" + listIterator.nextIndex());
        System.out.println("pre Index:" + listIterator.previousIndex());

        Vector vector = new Vector();

        LinkedList linkedList = new LinkedList();
    }

    public static void demo1() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
//        hashtable.put(null, "good!");
        LinkedHashMap hashMap = new LinkedHashMap();

        Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());

        List<String> list = new ArrayList<String>();
        Vector vector = new Vector();

        Map<String, String> viewMap = new HashMap<String, String>(10);
        viewMap.put("zhang", "zhang");
        viewMap.put("zhao", "zhao");
        viewMap.put("qian", "qian");

        Iterator<Map.Entry<String, String>> iterator = viewMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("key: " + entry.getKey());
            System.out.println("value: " + entry.getValue());
        }

        Set<String> keys = viewMap.keySet();
        for (String key : keys) {
            System.out.println("key: " + key);
        }
        Collection<String> values = viewMap.values();
        for (String v : values) {
            System.out.println("value: " + v);
        }
        Set<Map.Entry<String, String>> entry = viewMap.entrySet();
        for (Map.Entry<String, String> entry1 : viewMap.entrySet()) {
            System.out.println("key: " + entry1.getKey() + " value: " + entry1.getValue());
        }
    }

    public static void demo3() {
        StringBuilder str = new StringBuilder();

        StringBuffer buffer = new StringBuffer();

        char ch = '张';
        System.out.println(ch);

        char[] charArr = {'x', 'a', 'b'};
        String str1 = new String(charArr);

        byte[] bytes = new byte['s'];
        String str2 = new String(bytes);
        str2.length();
        str2.getBytes();

        Integer a = 6;
        Integer.toString(a);
        String s = String.valueOf(a);
        Integer.toString(a);
        int b = 30;
        long c = (long)a;
    }

    public static void demo4() {
        LinkedList list = new LinkedList<String>();

    }
}


/**
 * Created by zhangguoqing.zgq on 2017/1/22.
 * 用于测试对ArrayList的remove操作，以便分析其使用误区源码
 */
public class TestRemove {
//    public static void main(String[] args) {
//        List<String> list = new ArrayList<String>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        list.add("e");
//
//        //第一种remove方式
//        //remove的时候会重新调整数组的长度，相当于i后面的元素前移
//        //最后一个元素置为null，方便gc
////        for (int i = 0; i < list.size(); i++) {
////            list.remove(i);
////        }
//
//        //第二种remove方式
//        //抛出java.utility.ConcurrentModificationException异常
//        //具体原因在于调用list.remove()方法导致modCount和expectedModCount的值不一致。
////        for (String str : list) {
////            list.remove(str);
////        }
//
//        //第三种remove方式
//        //通过迭代器方式（这种方式在remove()时会设置expectedModCount = modCount）
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            iterator.next();
//            iterator.remove();
//        }
//
////        System.out.println(list.toString());
//    }

    public void test() {
        Person person = new Person();
    }
}

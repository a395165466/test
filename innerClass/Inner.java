/**
 * Created by zhangguoqing.zgq on 2017/5/17.
 */
public class Inner {
    private int age;

    private String name;

    private String sex;

    public Inner() {
        this.age = 18;
        this.name = "xiaoming";
        this.sex = "male";
    }
    public Inner(int age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public class InnerA {
        private int a;
        private String b;

        public void displayA() {
            System.out.println("InnerA");
        }
    }

    public static class InnerB {
        private int c;
        private String d;

        public void display() {
            System.out.println("Inner");
        }
        public static void displayB() {
            System.out.println("InnerB");
        }
    }

    public static void main(String[] args) {
        Inner inner = new Inner();
//        Inner.InnerA innerA = inner.new InnerA();
        Inner.InnerA innerA = new Inner().new InnerA();
        innerA.displayA();

        new Inner.InnerB().displayB();
        new Inner.InnerB().display();
        new InnerB().displayB();
        new InnerB().display();
//        Inner.InnerB innerB = new Inner.InnerB();
//        innerB.displayB();
    }
}


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangguoqing.zgq on 2017/10/9.
 * 自定义类加载器
 */
public class ClassLoaderTest extends URLClassLoader {
    private static Map<String,Class> map = new HashMap<String,Class>();

    public ClassLoaderTest(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    /**
     * 双亲委派逻辑在loadClass里完成。如果想要定制一个完全新的ClassLoader，不再遵循
     * 原有的双亲委派逻辑（如Pandora的类加载器），则需要覆盖该loadClass函数，重写类加载逻辑。
     * 即首先在一个叫MiddleWareMap (这里只是随便起的名字, 实际也并不是map) 的变量中查看
     * 是否已经加载了想要的中间件类, 如果有, 就直接返回该类, 如果没有, 会走普通的双亲委派
     * 模型机制, 请求parent去加载。
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(map.get(name) != null){
            System.out.println("load classcache from map name:"+name);
            return map.get(name);
        }

        return super.loadClass(name);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {//自定义在某目录下加载类文件
        Class<?> temp = this.findLoadedClass(name);

        if (temp == null) {//加载
            byte[] classData = getClassData(name);  //根据类的二进制名称,获得该class文件的字节码数组
            if (classData == null) {
                throw new ClassNotFoundException();
            }
            temp = defineClass(name, classData, 0, classData.length);
            map.put(temp.getName(), temp) ;
        }

        return temp;
    }

    private byte[] getClassData(String name) {
        InputStream is = null;
        try {
            String path = classNameToPath(name);
            URL url = new URL(path);
            byte[] buff = new byte[1024 * 4];
            int len = -1;
            is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((len = is.read(buff)) != -1) {
                baos.write(buff,0,len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private String classNameToPath(String name) {
        String classPath= this.getURLs()[0].toString() + name.replace(".", "/") + ".class";
        System.out.println("[Loaded----- "+name+" from "+classPath);
        return classPath ;
    }

    public static void main(String[] args) throws ClassNotFoundException,
            MalformedURLException,
            IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException, NoSuchFieldException {
        String className1 = "com.taobao.ata.test.TestRemove";
        System.out.println("-----------------begin-----------------");
        File file1 = new File("");

        ClassLoader loader = new ClassLoaderTest(new URL[]{file1.toURL()}, null);
        Class<?> class1 = loader.loadClass(className1);
        System.out.println("class1 name:" + class1.getClassLoader());

        System.out.println("ClassLoaderTest name:" + ClassLoaderTest.class.getClassLoader());

//        Class temp = Class.forName(className1, true, loader);
//        Object c = temp.newInstance();
//        Method m = temp.getDeclaredMethod("test");
//        m.invoke(c);

        Class<?> class2 = loader.loadClass(className1);
        System.out.println("class2 name:" + class1.getClassLoader());

        System.out.println("------------------end-------------------");


        TestRemove remove = new TestRemove();
        System.out.println("TestRemove name:" + remove.getClass().getClassLoader());

        System.out.println("System ClassLoader: " + getSystemClassLoader());

//        URL[] extURLs = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
//        for (int i = 0; i < extURLs.length; i++) {
//            System.out.println(extURLs[i]);
//        }

        System.out.println("classpath:" + System.getProperty("java.classpath"));
    }
}

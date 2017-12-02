import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zhangguoqing.zgq on 2017/3/27.
 * 测试Java常用的网络相关的类URL
 */
public class Url {
    public static void main(String[] args) {
        try {
            demo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sendGet("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sendPost("", "activity=110");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void demo() throws IOException {
        URL url = new URL("http://www.baidu.com");
        System.out.println(url.getPath());
        System.out.println(url.getContent());
        System.out.println(url.getFile());
        System.out.println(url.getHost());
        System.out.println(url.getPort());
        System.out.println(url.getProtocol());

        String tmp = null;
        StringBuilder str = new StringBuilder();

        //抓取页面内容
        InputStream inputStream = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((tmp = reader.readLine()) != null) {
            str.append(tmp);
        }
        System.out.println(str);

        inputStream.close();
    }

    //模拟Get请求
    public static void sendGet(String url) throws IOException{
        URL Url = new URL(url);
        URLConnection connection = Url.openConnection();

//        connection.setRequestProperty();//设置Request Property
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String tmp = null;
        StringBuilder builder = new StringBuilder();
        while ((tmp = reader.readLine()) != null) {
            builder.append(tmp);
        }

        System.out.println(builder);
        inputStream.close();
    }

    //模拟Post请求
    public static void sendPost(String url, String params) throws IOException {
        URL Url = new URL(url);
        URLConnection connection = Url.openConnection();

//        connection.setRequestProperty();//设置request Property
        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.connect();

//        PrintWriter writer = new PrintWriter(connection.getOutputStream());
//        writer.print(params);
//        writer.flush();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write(params);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String tmp = null;
        StringBuilder builder = new StringBuilder();
        while ((tmp = reader.readLine()) != null) {
            builder.append(tmp);
        }

        System.out.println(builder);
        reader.close();
        writer.close();
    }
}

/**
 * Created by zhangguoqing.zgq on 2017/4/16.
 */
public class Main {
    public static void main(String[] args) {
        HttpServer server = new HttpServer(1000, 9999);
        server.start();
    }
}

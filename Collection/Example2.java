import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangguoqing.zgq on 2017/3/15.
 * 测试泛型
 */
public class Example2 {
    public static void main(String[] args) {
        demo();
    }

    public static void demo() {
//        Pair<String>[] pair = new Pair<String>[10];

        String render = "<p><a href=\"http://baidu2-img.cn-hangzhou.img-pub.xx-inc.com/46c98305266d9d8fd5b72d9ae84d6c72.png\" target=\"_blank\"><img src=\"http://ata2-img.cn-hangzhou.img-pub.aliyun-inc.com/46c98305266d9d8fd5b72d9ae84d6c72.png\" alt=\"1.png\" title=\"1.png\"></a></p>\n" +
                "<p>在CES 2017大展上，Kino-mo公司的展台前面挤满了人。究竟是什么这么吸引人呢？原来这家公司利用全息裸眼方案 Kino-mo Holo Displays向观众展示了酷炫和惊艳的3D画面，令观众大饱眼福。";
        Pattern pattern = Pattern.compile("<img(.*?)src=('|\")(.*?)('|\")");
        Matcher matcher = pattern.matcher(render);
        if (matcher.find()) {
            System.out.println(matcher.group(0));//基于整个regx的匹配
            System.out.println(matcher.group(3));//基于分组3（.*?）的匹配
        }

        //获取网络图片的宽和高
        InputStream in = null;

        try {
            in = new URL("").openStream();

            BufferedImage sourceImg = ImageIO.read(in);
            System.out.println(sourceImg.getWidth());
            System.out.println(sourceImg.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

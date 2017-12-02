import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguoqing.zgq on 2017/3/27.
 * 基于HttpClient类的测试
 */
public class HttpClientTest {
    public static void main(String[] args) {
        try {
            HttpGet("");//可以是网页也可以是接口
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            httpPost("", "activity=110");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static String HttpGet(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = httpClient.execute(httpGet);

        String result = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }

    /**
     * 通过Post获取数据
     * @param url
     * @param params
     * @return
     * @throws java.io.IOException
     */
    public static String HttpPost(String url, Map<String, String> params) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //创建参数列表
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry item : params.entrySet()) {
            list.add(new BasicNameValuePair(item.getKey().toString(), item.getValue().toString()));
        }

        //url格式编码
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,"UTF-8");
        httpPost.setEntity(uefEntity);

        HttpResponse response = client.execute(httpPost);

        String result = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }
}

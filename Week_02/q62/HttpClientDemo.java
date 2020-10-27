package Week_02.q62;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientDemo {
    public static void main(String[] args) {
        // 创建http get请求
        HttpGet httpGet = new HttpGet("http://localhost:8088/api/hello");

        try (
            // 创建http对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建http response
            CloseableHttpResponse response = httpClient.execute(httpGet);
            ) 
        {
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串
                String body = EntityUtils.toString(entity, "utf-8");
                System.out.println(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
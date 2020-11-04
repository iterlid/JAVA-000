package io.github.kimmking.gateway.outbound.httpclient;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpOutboundHandler {

    private String backendUrl;
    
    public HttpOutboundHandler(String backendUrl){
        this.backendUrl = backendUrl.endsWith("/")?backendUrl.substring(0,backendUrl.length()-1):backendUrl;
        //HttpGet httpGet = new HttpGet("http://localhost:8088/api/hello");
        /*
        HttpGet httpGet = new HttpGet(backendUrl);
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
        }*/
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = this.backendUrl + fullRequest.uri();
        HttpGet httpGet = new HttpGet(url);
        try (
            CloseableHttpClient httpClient = HttpClients.createDefault();
            httpGet.addHeader((Header)fullRequest.headers());
            CloseableHttpResponse response = httpClient.execute(httpGet);
        ){
            // 把 内容写入到ctx 
//              byte[] body = EntityUtils.toByteArray(response.getEntity());
//            System.out.println(new String(body));
//            System.out.println(body.length);

            handleResponse(fullRequest, ctx, response);


      } catch (Exception e) {
          e.printStackTrace();
      }
    }
 
    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final CloseableHttpResponse endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
            response.headers().setInt("Content-Length", response.content().readableBytes());

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                // 后续的过滤器。

                
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }

}

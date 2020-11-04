package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class DefaultRequestFilter implements HttpRequestFilter {
    

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)
    {
        // DefaultRequestFilter DefaultRequestFilter
        // 这个地方可以对数据进行处理。
        fullRequest.headers().set("hello_filter", "iterlid");

    }
    
}

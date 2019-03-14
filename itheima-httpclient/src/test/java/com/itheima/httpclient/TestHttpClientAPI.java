package com.itheima.httpclient;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class TestHttpClientAPI {
    @Test
    public void testGet() throws IOException, URISyntaxException {
        HttpClientAPI httpClientAPI = new HttpClientAPI();
        Map<String, Object> params = new HashMap<>();
        params.put("page",2);
        params.put("rows",10);
        HttpResponse httpResponse = httpClientAPI.getWithParams("http://localhost:8081/item/list", params);
        System.out.println(httpResponse);
    }

    @Test
    public void testPost() throws IOException {
        HttpClientAPI httpClientAPI = new HttpClientAPI();
        Map<String, Object> params = new HashMap<>();
        params.put("cid",3);
        params.put("title", "httpclient2");
        params.put("sellPoint", "asfdafaf");
        params.put("price", "1200");
        params.put("num", "213");
        params.put("itemParams","[{\"group\":\"主体\",\"params\":[{\"k\":\"章节\",\"v\":\"\"},{\"k\":\"页数\",\"v\":\"\"}]},{\"group\":\"尺寸\",\"params\":[{\"k\":\"大小\",\"v\":\"\"},{\"k\":\"厚度\",\"v\":\"\"}]}]");
        HttpResponse httpResponse = httpClientAPI.postWithParams("http://localhost:8081/item/save", params);
        System.out.println(httpResponse);
    }
}

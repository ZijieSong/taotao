package com.itheima.httpclient;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpClientAPI {
    public HttpResponse getWithParams(String url, Map<String, Object> params) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(url);
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach((k, v) -> builder.setParameter(k, v.toString()));
        }
        URI uri = builder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(response.getStatusLine().getStatusCode());
        if(response.getEntity()!=null)
            httpResponse.setContent(EntityUtils.toString(response.getEntity(),"utf-8"));
        return httpResponse;
    }

    public HttpResponse getWithoutParams(String url) throws IOException, URISyntaxException {
        return getWithParams(url,null);
    }

    public HttpResponse postWithParams(String url, Map<String, Object> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);

        if(!CollectionUtils.isEmpty(params)) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            params.forEach((k, v) -> nameValuePairs.add(new BasicNameValuePair(k, v.toString())));
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs);
            httpPost.setEntity(urlEncodedFormEntity);
        }

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        HttpResponse response = new HttpResponse();
        response.setStatus(httpResponse.getStatusLine().getStatusCode());
        if(httpResponse.getEntity()!=null)
            response.setContent(EntityUtils.toString(httpResponse.getEntity(),"utf-8"));

        return response;
    }

    public HttpResponse postWithoutParams(String url) throws IOException {
        return postWithParams(url,null);
    }
}

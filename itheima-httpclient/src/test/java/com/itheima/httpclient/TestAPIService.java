package com.itheima.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

public class TestAPIService {
	@Test
	public void test() throws Exception{
		APIService service = new APIService();
		//HttpResult doGet = service.doGet("http://www.163.com");
		Map<String, Object> map  = new HashMap<>();
//		map.put("page", "1");
//		map.put("rows", "60");
//		HttpResult doGet = service.doGet("http://localhost:8081/item/list", map);
		
		//HttpResult doPost = service.doPost("http://www.baidu.com");
		
	       map.put("cid","560");
	       map.put("title", "httpclient2");
	       map.put("sellPoint", "asfdafaf");
	       map.put("price", "1200");
	       map.put("num", "213");
		HttpResult doPost = service.doPost("http://localhost:8081/item/save", map);
		
		System.out.println(doPost.getBody());
		System.out.println(doPost.getCode());
		
		
		
	}
}

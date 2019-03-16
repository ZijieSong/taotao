package com.taotao.content.service;

import com.taotao.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JedisClientTest {
    @Test
    public void testJedisClient(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("jedisClient","jedisClient!");
        System.out.println(jedisClient.get("jedisClient"));
    }
}

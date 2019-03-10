package com.taotao.content.service;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {
    @Test
    public void testJedis(){
        Jedis jedis = new Jedis("10.211.55.3",6379);
        jedis.set("a","a");
        System.out.println(jedis.get("a"));
        jedis.close();
    }
    @Test
    public void testJedisPool(){
        JedisPool jedisPool = new JedisPool("10.211.55.3",6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("b","b");
        System.out.println(jedis.get("b"));
        jedis.close();
        jedisPool.close();
    }
    @Test
    public void testJedisCluster(){
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("10.211.55.3",7001));
        nodes.add(new HostAndPort("10.211.55.3",7002));
        nodes.add(new HostAndPort("10.211.55.3",7003));
        nodes.add(new HostAndPort("10.211.55.3",7004));
        nodes.add(new HostAndPort("10.211.55.3",7005));
        nodes.add(new HostAndPort("10.211.55.3",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        jedisCluster.set("cluster","yes i am");
        System.out.println(jedisCluster.get("cluster"));

        //系统关闭前，关闭JedisCluster对象。
        jedisCluster.close();
    }
}

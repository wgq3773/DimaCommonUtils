package com.dima.commons.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestRedis {

	// 测试单机版redis
	@Test
	public void testJedis() throws Exception {
		// 第一步：创建一个Jedis对象。需要指定服务端的ip及端口。
		Jedis jedis = new Jedis("47.107.142.11", 6379);//阿里云服务器
		// 第二步：使用Jedis对象操作数据库，每个redis命令对应一个方法。
		jedis.set("hello", "hello redis");
		String result = jedis.get("hello");
		// 第三步：打印结果。
		System.out.println(result);
		// 第四步：关闭Jedis
		jedis.close();
	}
	
	//连接单机版使用连接池
    @Test
    public void testJedisPool() throws Exception {
        // 第一步：创建一个JedisPool对象。需要指定服务端的ip及端口。
//        JedisPool jedisPool = new JedisPool("192.168.206.128", 6379);
        JedisPool jedisPool = new JedisPool("47.107.142.11", 6379);//阿里云服务器
        // 第二步：从JedisPool中获得Jedis对象。
        Jedis jedis = jedisPool.getResource();
        // 第三步：使用Jedis操作redis服务器。
        jedis.set("jedis", "test");
        String result = jedis.get("jedis");
        System.out.println(result);
        // 第四步：操作完毕后关闭jedis对象，连接池回收资源。
        jedis.close();
        // 第五步：关闭JedisPool对象。
        jedisPool.close();
    }
    
  //使用集群版连接redis
//    @Test
//    public void testJedisCluster() throws Exception {
//        // 第一步：使用JedisCluster对象。需要一个Set<HostAndPort>参数。Redis节点的列表。
//        Set<HostAndPort> nodes = new HashSet<>();
//        nodes.add(new HostAndPort("192.168.206.128", 7001));
//        nodes.add(new HostAndPort("192.168.206.128", 7002));
//        nodes.add(new HostAndPort("192.168.206.128", 7003));
//        nodes.add(new HostAndPort("192.168.206.128", 7004));
//        nodes.add(new HostAndPort("192.168.206.128", 7005));
//        nodes.add(new HostAndPort("192.168.206.128", 7006));
//        JedisCluster jedisCluster = new JedisCluster(nodes);
//        // 第二步：直接使用JedisCluster对象操作redis。在系统中单例存在。
//        jedisCluster.set("hello", "100");
//        String result = jedisCluster.get("hello");
//        // 第三步：打印结果
//        System.out.println(result);
//        // 第四步：系统关闭前，关闭JedisCluster对象。
//        jedisCluster.close();
//    }
}
package com.chenjj.redis.datatype;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 散列类型
 * @author Administrator
 *
 */
public class HashTest {
	private JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.19.128");
	Jedis jedis = null;

	public Jedis getResource() {
		jedis = pool.getResource();
		return jedis;
	}

	public void closeResource(Jedis jedis) {
		jedis.close();
	}

	@Test
	public void test() {
		Jedis jedis = getResource();
		String key = "learning redis";
		jedis.hset(key, "publisher", "zhangsan");
		jedis.hset(key, "author", "Vinoo Das");
		System.out.println(jedis.hgetAll(key));
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("ISBN", "XX-XX-XX-XX");
		attributes.put("tags", "Redis,NoSQL");
		attributes.put("pages", "250");
		attributes.put("weight", "200.56");
		jedis.hmset(key, attributes);// 设置多个field
		System.out.println(jedis.hgetAll(key));
		System.out.println(jedis.hget(key, "publisher"));
		System.out.println(jedis.hmget(key, "publisher", "author"));
		System.out.println(jedis.hvals(key));
		System.out.println(jedis.hget(key, "publisher"));
		System.out.println(jedis.hkeys(key));
		System.out.println(jedis.hexists(key, "cost"));
		System.out.println(jedis.hlen(key));
		System.out.println(jedis.hincrBy(key, "pages", 10));
		System.out.println(jedis.hincrByFloat(key, "weight", 1.1) + "gms");
		System.out.println(jedis.hdel(key, "weight-in-gms"));
		System.out.println(jedis.hgetAll(key));
		closeResource(jedis);
	}
}

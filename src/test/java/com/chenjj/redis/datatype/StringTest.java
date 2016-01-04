package com.chenjj.redis.datatype;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 字符串类型
 * @author Administrator
 *
 */
public class StringTest {

	private JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.19.128");
	Jedis jedis = null;

	public Jedis getResource() {
		jedis = pool.getResource();
		return jedis;
	}

	public void closeResource(Jedis jedis) {
		jedis.close();
	}

	@SuppressWarnings("static-access")
	@Test
	public void test() throws InterruptedException {
		Jedis jedis = getResource();
		String key = "mykey";
		jedis.set(key, "Hello World");
		System.out.println("1) " + jedis.get(key));
		jedis.append(key, "and this is a bright sunny day ");
		System.out.println("2) " + jedis.get(key));
		String substring = jedis.getrange(key, 0, 5);
		System.out.println("3) " + "substring value = " + substring);
		String key1 = "mykey1";
		jedis.set(key1, "Let's learn redis");
		for (String value : jedis.mget(key, key1)) {
			System.out.println("4) " + " - " + value);
		}
		jedis.mset("mykey2", "let's start with string", "mykey3", "then we will learn other data types");
		for (String value : jedis.mget(key, key1, "mykey2", "mykey3")) {
			System.out.println("5) " + " -- " + value);
		}
		jedis.setnx("mykey4", "next in line is hashmaps");
		System.out.println("6) " + jedis.get("mykey4"));
		jedis.msetnx("mykey4", "next in line is sorted sets");
		System.out.println("7) " + jedis.get("mykey4"));
		jedis.psetex("mykey5", 1000L, "this message will self destruct in 1000 milliseconds");
		System.out.println("8) " + jedis.get("mykey5") + ": " + jedis.pttl("mykey5"));
		Thread.currentThread().sleep(1200);
		System.out.println("8) " + jedis.get("mykey5"));
		long length = jedis.strlen(key);
		System.out.println("9) " + " the length of the string 'mykey' is " + length);
		this.closeResource(jedis);
	}
}

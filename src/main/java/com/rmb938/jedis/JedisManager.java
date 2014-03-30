package com.rmb938.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisManager {

    private static JedisPool jedisPool;

    public static void connectToRedis(String address) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, address);
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void returnJedis(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

}

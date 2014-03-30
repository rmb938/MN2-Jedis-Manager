package com.rmb938.jedis;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetDelegate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;

public class JedisManager {

    private static ArrayList<NetDelegate> netDelegates = new ArrayList<>();

    private static JedisPool jedisPool;

    public static void connectToRedis(String address) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, address);
    }

    public static void setUpDelegates() {
        for (NetChannel channel : NetChannel.values()) {
            netDelegates.add(new NetDelegate(channel));
        }
    }

    public static void shutDown() {
        for (NetDelegate netDelegate : netDelegates) {
            netDelegate.unsubscribe();
        }
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void returnJedis(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

}

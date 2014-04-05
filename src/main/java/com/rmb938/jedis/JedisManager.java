package com.rmb938.jedis;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetDelegate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.logging.Logger;

public class JedisManager {

    private final static Logger logger = Logger.getLogger(JedisManager.class.getName());

    private static ArrayList<NetDelegate> netDelegates = new ArrayList<>();

    private static JedisPool jedisPool;

    public static void connectToRedis(String address) {
        logger.info("Connecting to Redis server at "+address);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        config.setMaxTotal(200);
        config.setMaxIdle(50);
        config.setTestWhileIdle(true);
        config.setMaxWaitMillis(500);
        jedisPool = new JedisPool(config, address);
    }

    public static void setUpDelegates() {
        for (NetChannel channel : NetChannel.values()) {
            netDelegates.add(new NetDelegate(channel));
        }
    }

    public static void shutDown() {
        logger.info("Shutting Down Net Delegates");
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

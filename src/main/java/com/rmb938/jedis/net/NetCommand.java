package com.rmb938.jedis.net;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.JedisManager;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommand.class.getName());
    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    private final HashMap<String, Object> args;
    private final String name;
    private final NetChannel netChannel;

    private final JSONObject jsonObject;

    public NetCommand(String name, NetChannel netChannel) {
        Preconditions.checkNotNull(name, "Net Command name cannot be null");
        Preconditions.checkNotNull(netChannel, "Net Command channel cannot be null");
        Preconditions.checkArgument(name.trim().isEmpty() == false, "Net Command name cannot be blank");
        this.name = name;
        this.netChannel = netChannel;
        this.jsonObject = new JSONObject();
        this.args = new HashMap<>();
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void addArg(String arg, Object o) {
        this.args.put(arg, o);
    }

    public abstract void buildJSON();

    private void addCommandInfo() {
        try {
            jsonObject.put("command", name);
            JSONObject argsObject = new JSONObject(args);
            jsonObject.put("data", argsObject);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    public void flush() {
        addCommandInfo();
        NetCommand.getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = null;
                try {
                    jedis = JedisManager.getJedis();
                    jedis.publish(netChannel.name(), jsonObject.toString());
                } catch (Exception e) {
                    logger.severe("Unable to send NetCommand "+netChannel.name()+" Contents: "+jsonObject.toString());
                } finally {
                    if (jedis != null) {
                        JedisManager.returnJedis(jedis);
                    }
                }
            }
        });
    }
}

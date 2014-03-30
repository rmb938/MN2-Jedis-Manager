package com.rmb938.jedis.net;

import com.rmb938.jedis.JedisManager;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommand.class.getName());

    private final HashMap<String, Object> args;
    private final String name;
    private final NetChannel netChannel;

    private final JSONObject jsonObject;

    public NetCommand(String name, NetChannel netChannel) {
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
        Jedis jedis = JedisManager.getJedis();
        String id = jsonObject.optString("from", jsonObject.optString("to"));
        jedis.publish(netChannel.name()+"."+id, jsonObject.toString());
        JedisManager.returnJedis(jedis);
    }
}

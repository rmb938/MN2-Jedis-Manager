package com.rmb938.jedis.net;

import com.rmb938.jedis.JedisManager;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

public abstract class NetCommand {
    /**
     * The arguments of the command we're going to send.
     */
    private final HashMap<String, Object> args;
    /**
     * The name of the command.
     */
    private final String name;

    private final NetChannel netChannel;

    public NetCommand(String name, NetChannel netChannel) {
        this.name = name;
        this.netChannel = netChannel;
        this.args = new HashMap<>();
    }

    /**
     * Add an argument
     * @param arg With name
     * @param o and value
     * @return this object.
     */
    public void addArg(String arg, Object o) {
        this.args.put(arg, o);
    }

    public abstract JSONObject buildJSON();


    public final JSONObject addCommandInfo(JSONObject jsonObject) {
        try {
            jsonObject.put("command", name);
            JSONObject argsObject = new JSONObject(args);
            jsonObject.put("data", argsObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

    public void flush(JSONObject jsonObject) {
        Jedis jedis = JedisManager.getJedis();
        String id = jsonObject.optString("from", jsonObject.optString("to"));
        jedis.publish(netChannel.name()+"."+id, jsonObject.toString());
        JedisManager.returnJedis(jedis);
    }
}

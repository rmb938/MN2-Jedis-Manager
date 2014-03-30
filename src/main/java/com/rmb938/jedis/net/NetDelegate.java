package com.rmb938.jedis.net;

import com.rmb938.jedis.JedisManager;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;

public class NetDelegate extends JedisPubSub implements Runnable {

    private final NetChannel netChannel;

    public NetDelegate(NetChannel netChannel) {
        this.netChannel = netChannel;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void onMessage(String channel, String data) {
        NetChannel netChannel = NetChannel.getChannelFromName(channel.split("\\.")[0]);
        if (netChannel == null) {
            System.out.println("Unknown NetChannel for "+channel);
            return;
        }
        ArrayList<NetCommandHandler> commandHandlers = NetCommandHandler.getHandlers(netChannel);
        if (commandHandlers == null) {
            return;
        }
        if (commandHandlers.isEmpty()) {
            return;
        }
        JSONObject obj;
        try {
            obj = new JSONObject(data);
        } catch (JSONException ex) {
            ex.printStackTrace();
            return;
        }
        for (NetCommandHandler commandHandler : commandHandlers) {
            commandHandler.handle(obj);
        }
    }

    @Override
    public void onPMessage(String s, String s2, String s3) {
    }

    @Override
    public void onSubscribe(String s, int i) {
    }

    @Override
    public void onUnsubscribe(String s, int i) {
    }

    @Override
    public void onPUnsubscribe(String s, int i) {
    }

    @Override
    public void onPSubscribe(String s, int i) {
    }

    @Override
    public void run() {
        System.out.println("Starting NetDelegate for channel: "+netChannel.name());
        Jedis jedis = JedisManager.getJedis();
        jedis.subscribe(this, netChannel.name());
    }
}

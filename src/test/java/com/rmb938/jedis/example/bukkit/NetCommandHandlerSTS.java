package com.rmb938.jedis.example.bukkit;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommandHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.logging.Level;

public class NetCommandHandlerSTS extends NetCommandHandler {

    private final TestPlugin plugin;

    public NetCommandHandlerSTS(TestPlugin plugin) {
        NetCommandHandler.addHandler(NetChannel.SERVER_TO_SERVER, this);
        this.plugin = plugin;
    }

    @Override
    public void handle(JSONObject jsonObject) {
        try {
            String fromServer = jsonObject.getString("from");
            String toServer = jsonObject.getString("to");

            if (toServer.equals(plugin.getServerName()+".*") == false && toServer.equals(plugin.getServerName()+"."+plugin.getServer().getPort()) == false) {
                return;
            }

            String command = jsonObject.getString("command");
            HashMap<String, Object> objectHashMap = objectToHashMap(jsonObject.getJSONObject("data"));
            switch (command) {
                case "status":
                    int currentPlayers = (Integer) objectHashMap.get("currentPlayers");
                    int maxPlayers = (Integer) objectHashMap.get("maxPlayers");
                    String status = (String) objectHashMap.get("status");
                    break;
                default:
                    plugin.getLogger().warning("Unknown STS Command: " + command);
            }
        } catch (JSONException e) {
            plugin.getLogger().log(Level.SEVERE, null, e);
        }
    }
}

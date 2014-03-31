package com.rmb938.jedis.example.bungee;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommandHandler;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.logging.Level;

public class NetCommandHandlerSTB extends NetCommandHandler {

    private final TestPlugin plugin;

    public NetCommandHandlerSTB(TestPlugin plugin) {
        NetCommandHandler.addHandler(NetChannel.SERVER_TO_BUNGEE, this);
        this.plugin = plugin;
    }

    @Override
    public void handle(JSONObject jsonObject) {
        try {
            String fromServer = jsonObject.getString("from");
            String command = jsonObject.getString("command");
            HashMap<String, Object> objectHashMap = objectToHashMap(jsonObject.getJSONObject("data"));
            switch (command) {
                case "addServer":
                    String addr = (String) objectHashMap.get("addr");
                    int port = (Integer) objectHashMap.get("port");

                    ServerInfo serverInfo = plugin.getProxy().constructServerInfo(fromServer, new InetSocketAddress(addr, port), fromServer, false);
                    plugin.getProxy().getServers().put(fromServer, serverInfo);
                    break;
                case "sendTo":
                    String playerName = (String) objectHashMap.get("playerName");
                    String serverName = (String) objectHashMap.get("serverName");
                    ProxiedPlayer player = plugin.getProxy().getPlayer(playerName);

                    if (player == null) {
                        return;
                    }

                    if (player.getServer().getInfo().getName().equalsIgnoreCase(serverName)) {
                        plugin.getLogger().warning("Trying to teleport player to same server " + serverName);
                        return;
                    }
                    serverInfo = plugin.getProxy().getServerInfo(serverName);

                    if (serverInfo == null) {
                        player.sendMessage(new TextComponent("The server you tried to join is null please contact a staff member."));
                        return;
                    }

                    plugin.getLogger().info("Connecting player " + playerName + " to server " + serverInfo);
                    player.connect(plugin.getProxy().getServerInfo(serverName));
                    break;
                default:
                    plugin.getLogger().warning("Unknown STB Command: " + command);
            }
        } catch (JSONException e) {
            plugin.getLogger().log(Level.SEVERE, null, e);
        }
    }
}

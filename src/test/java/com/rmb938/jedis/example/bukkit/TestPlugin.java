package com.rmb938.jedis.example.bukkit;

import com.rmb938.jedis.JedisManager;
import com.rmb938.jedis.net.NetCommandHandler;
import com.rmb938.jedis.net.command.bungee.NetCommandBTS;
import com.rmb938.jedis.net.command.server.NetCommandSTB;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    private String serverName;

    public void onEnable() {
        serverName = "someServerName";
        JedisManager.connectToRedis("127.0.0.1");
        new NetCommandHandlerSTS(this);
        JedisManager.setUpDelegates();

    }

    public String getServerName() {
        return serverName;
    }

    public void onDisable() {
        JedisManager.shutDown();
    }

    public void teleportPlayerToServer(String playerName) {
        NetCommandSTB netCommandSTB = new NetCommandSTB("sendTo", "serverName."+this.getServer().getPort());
        netCommandSTB.addArg("playerName", playerName);
        netCommandSTB.addArg("serverName", "anotherServer."+25864);
        netCommandSTB.flush();
    }

}

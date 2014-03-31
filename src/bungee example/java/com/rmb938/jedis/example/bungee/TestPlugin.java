package com.rmb938.jedis.example.bungee;

import com.rmb938.jedis.JedisManager;
import com.rmb938.jedis.net.command.bungee.NetCommandBTS;
import net.md_5.bungee.api.plugin.Plugin;

public class TestPlugin extends Plugin {

    public void onEnable() {
        JedisManager.connectToRedis("127.0.0.1");
        new NetCommandHandlerSTB(this);
        JedisManager.setUpDelegates();

    }

    public void onDisable() {
        JedisManager.shutDown();
    }

    public void messageToServer() {
        NetCommandBTS netCommandBTS = new NetCommandBTS("commandName", "", "testServer.25758");
        netCommandBTS.addArg("someArg", 5);
        netCommandBTS.addArg("anotherArg", new Object());
        netCommandBTS.flush();
    }

}

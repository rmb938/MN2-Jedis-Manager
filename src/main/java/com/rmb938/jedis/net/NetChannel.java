package com.rmb938.jedis.net;

public enum NetChannel {

    //SERVER
    SERVER_TO_BUNGEE,
    SERVER_TO_SERVER,
    SERVER_TO_SERVER_CONTROLLER,

    //SERVER CONTROLLER
    SERVER_CONTROLLER_TO_SERVER_CONTROLLER,
    SERVER_CONTROLLER_TO_SERVER,
    SERVER_CONTROLLER_TO_BUNGEE,

    //BUNGEE
    BUNGEE_TO_BUNGEE,
    BUNGEE_TO_SERVER,
    BUNGEE_TO_SERVER_CONTROLLER;

    public static NetChannel getChannelFromName(String name) {
        for (NetChannel channel : NetChannel.values()) {
            if (channel.name().equals(name)) {
                return channel;
            }
        }
        return null;
    }

}

package com.rmb938.jedis.net.command.servercontroller;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSCTS extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSCTS.class.getName());

    private final int toServerPort;
    private final String fromServerController;

    /**
     * A Command that goes from a server controller to a server
     * @param name - the name of the command
     * @param fromServerController - the server controller's internal ip address
     * @param toServerPort - server port
     *                     If toServerPort == -1 message is a broadcast to all
     *                     servers managed by the server controller
     */
    public NetCommandSCTS(String name, String fromServerController, int toServerPort) {
        super(name, NetChannel.SERVER_CONTROLLER_TO_BUNGEE);
        this.fromServerController = fromServerController;
        this.toServerPort = toServerPort;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerController);
            getJsonObject().put("to", toServerPort);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

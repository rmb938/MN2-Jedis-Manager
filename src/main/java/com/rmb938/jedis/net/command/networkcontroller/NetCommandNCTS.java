package com.rmb938.jedis.net.command.networkcontroller;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandNCTS extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandNCTS.class.getName());

    private final int toServerPort;

    /**
     * A Command that goes from the network controller to a server
     * @param name - the name of the command
     * @param toServerPort - server port
     *                     If toServerPort == -1 message is a broadcast
     *                     to all servers
     */
    public NetCommandNCTS(String name, int toServerPort) {
        super(name, NetChannel.NETWORK_CONTROLLER_TO_SERVER);
        Preconditions.checkNotNull(toServerPort, "Net Command NCTS toServerPort cannot be null");
        Preconditions.checkArgument(toServerPort >= -1, "Net Command NCTS toServerPort must be greater or equal to -1");
        this.toServerPort = toServerPort;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("to", toServerPort);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

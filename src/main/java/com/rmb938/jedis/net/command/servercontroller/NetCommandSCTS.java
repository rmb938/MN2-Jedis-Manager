package com.rmb938.jedis.net.command.servercontroller;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSCTS extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSCTS.class.getName());

    private final String toServerName;
    private final String fromServerController;

    /**
     * A Command that goes from a server controller to a server
     * @param name - the name of the command
     * @param fromServerController - the server controller's internal ip address
     * @param toServerName - format serverName.port
     *                     If port == * command is a broadcast to all servers
     */
    public NetCommandSCTS(String name, String fromServerController, String toServerName) {
        super(name, NetChannel.SERVER_CONTROLLER_TO_BUNGEE);
        Preconditions.checkNotNull(fromServerController, "Net Command SCTS fromServerController cannot be null");
        Preconditions.checkNotNull(toServerName, "Net Command SCTS toServerPort cannot be null");
        Preconditions.checkArgument(fromServerController.trim().isEmpty() == false, "Net Command SCTS fromServerController cannot be empty");
        Preconditions.checkArgument(toServerName.trim().isEmpty() == false, "Net Command SCTS toServerName cannot be empty");
        this.fromServerController = fromServerController;
        this.toServerName = toServerName;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerController);
            getJsonObject().put("to", toServerName);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

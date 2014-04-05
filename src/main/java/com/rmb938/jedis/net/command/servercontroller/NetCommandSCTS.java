package com.rmb938.jedis.net.command.servercontroller;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSCTS extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSCTS.class.getName());

    private final String toServerUUID;
    private final String fromServerController;

    /**
     * A Command that goes from a server controller to a server
     * @param name - the name of the command
     * @param fromServerController - the server controller's internal ip address
     * @param toServerUUID - serverUUID
     *                     If toServerUUID == * command is a broadcast to all servers
     *                     run by this controller
     */
    public NetCommandSCTS(String name, String fromServerController, String toServerUUID) {
        super(name, NetChannel.SERVER_CONTROLLER_TO_SERVER);
        Preconditions.checkNotNull(fromServerController, "Net Command SCTS fromServerController cannot be null");
        Preconditions.checkNotNull(toServerUUID, "Net Command SCTS toServerPort cannot be null");
        Preconditions.checkArgument(fromServerController.trim().isEmpty() == false, "Net Command SCTS fromServerController cannot be empty");
        Preconditions.checkArgument(toServerUUID.trim().isEmpty() == false, "Net Command SCTS toServerUUID cannot be empty");
        this.fromServerController = fromServerController;
        this.toServerUUID = toServerUUID;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerController);
            getJsonObject().put("to", toServerUUID);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

package com.rmb938.jedis.net.command.server;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSTSC extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSTSC.class.getName());

    private final String toServerController;
    private final String fromServerName;

    /**
     * A Command that goes from a server to its server controller
     * @param name - name of the command
     * @param fromServerName - format serverName.port
     *                     If port == * command is a broadcast to all servers
     * @param toServerController - the internal IP of the server controller
     */
    public NetCommandSTSC(String name, String fromServerName, String toServerController) {
        super(name, NetChannel.SERVER_TO_SERVER_CONTROLLER);
        Preconditions.checkNotNull(toServerController, "Net Command STSC toServerController cannot be null");
        Preconditions.checkNotNull(fromServerName, "Net Command STSC fromServerName cannot be null");
        Preconditions.checkArgument(fromServerName.trim().isEmpty() == false, "Net Command STSC fromServerName cannot be empty");
        Preconditions.checkArgument(toServerController.trim().isEmpty() == false, "Net Command STSC toServerController cannot be empty");
        this.toServerController = toServerController;
        this.fromServerName = fromServerName;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerName);
            getJsonObject().put("to", toServerController);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

}

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
    private final int fromServerPort;

    /**
     * A Command that goes from a server to its server controller
     * @param name - name of the command
     * @param fromServerPort - server port
     * @param toServerController - the internal IP of the server controller
     */
    public NetCommandSTSC(String name, int fromServerPort, String toServerController) {
        super(name, NetChannel.SERVER_TO_SERVER_CONTROLLER);
        Preconditions.checkNotNull(toServerController, "Net Command STSC toServerController cannot be null");
        Preconditions.checkNotNull(fromServerPort, "Net Command STSC fromServerName cannot be null");
        Preconditions.checkArgument(fromServerPort > 0, "Net Command STSC fromServerPort must be greater than 0");
        Preconditions.checkArgument(toServerController.trim().isEmpty() == false, "Net Command STSC toServerController cannot be empty");
        this.toServerController = toServerController;
        this.fromServerPort = fromServerPort;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerPort);
            getJsonObject().put("to", toServerController);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

}

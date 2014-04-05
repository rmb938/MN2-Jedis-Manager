package com.rmb938.jedis.net.command.servercontroller;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSCTSC extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSCTSC.class.getName());

    private final String toServerController;
    private final String fromServerController;

    /**
     * A Command that goes from a server controller to a server
     * @param name - the name of the command
     * @param fromServerController - the server controller's internal ip address
     * @param toServerController - the server controller's internal ip address
     *                     If toServerController == * command is a broadcast to all servers controllers
     */
    public NetCommandSCTSC(String name, String fromServerController, String toServerController) {
        super(name, NetChannel.SERVER_CONTROLLER_TO_SERVER_CONTROLLER);
        Preconditions.checkNotNull(fromServerController, "Net Command SCTS fromServerController cannot be null");
        Preconditions.checkNotNull(toServerController, "Net Command SCTS toServerController cannot be null");
        Preconditions.checkArgument(fromServerController.trim().isEmpty() == false, "Net Command SCTS fromServerController cannot be empty");
        Preconditions.checkArgument(toServerController.trim().isEmpty() == false, "Net Command SCTS toServerController cannot be empty");
        this.fromServerController = fromServerController;
        this.toServerController = toServerController;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerController);
            getJsonObject().put("to", toServerController);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

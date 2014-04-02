package com.rmb938.jedis.net.command.networkcontroller;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandNCTSC extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandNCTSC.class.getName());

    private final String toServerController;

    /**
     * A Command that goes from the network controller to a server controller
     * @param name - the name of the command
     * @param toServerController - the internal IP of the server controller
     */
    public NetCommandNCTSC(String name, String toServerController) {
        super(name, NetChannel.NETWORK_CONTROLLER_TO_SERVER_CONTROLLER);
        Preconditions.checkNotNull(toServerController, "Net Command NCTSC toServerController cannot be null");
        Preconditions.checkArgument(toServerController.trim().isEmpty() == false, "Net Command NCTSC toServerController cannot be empty");
        this.toServerController = toServerController;
    }

    public void buildJSON() {
        try {
            getJsonObject().put("to", toServerController);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

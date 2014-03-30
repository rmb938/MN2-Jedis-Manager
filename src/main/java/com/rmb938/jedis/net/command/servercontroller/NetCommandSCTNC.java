package com.rmb938.jedis.net.command.servercontroller;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSCTNC extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSCTB.class.getName());

    private final String fromServerController;

    /**
     * A Command that goes from a server controller to the network controller
     * @param name - the name of the command
     * @param fromServerController - the server controller's internal IP address
     */
    public NetCommandSCTNC(String name, String fromServerController) {
        super(name, NetChannel.SERVER_CONTROLLER_TO_NETWORK_CONTROLLER);
        this.fromServerController = fromServerController;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerController);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

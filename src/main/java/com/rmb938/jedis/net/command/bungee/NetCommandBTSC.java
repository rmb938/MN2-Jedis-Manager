package com.rmb938.jedis.net.command.bungee;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandBTSC extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandBTS.class.getName());

    private final String fromBungee;

    /**
     * A Command that goes from a bungee instance to its server controller
     * @param name - the name of the command
     * @param fromBungee - the bungee instance internal IP address
     */
    public NetCommandBTSC(String name, String fromBungee) {
        super(name, NetChannel.BUNGEE_TO_SERVER_CONTROLLER);
        this.fromBungee = fromBungee;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromBungee);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

}

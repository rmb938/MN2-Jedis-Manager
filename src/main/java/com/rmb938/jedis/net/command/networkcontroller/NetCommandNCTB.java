package com.rmb938.jedis.net.command.networkcontroller;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandNCTB extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandNCTB.class.getName());

    private final String toBungee;

    /**
     * A Command that goes from the network controller to a bungee instance
     * @param name - the name of the command
     * @param toBungee - the bungee instance internal IP address
     *                 If toBungee == * message is a broadcast to all bungee instances
     */
    public NetCommandNCTB(String name, String toBungee) {
        super(name, NetChannel.NETWORK_CONTROLLER_TO_BUNGEE);
        Preconditions.checkNotNull(toBungee, "Net Command NCTB toBungee cannot be null");
        Preconditions.checkArgument(toBungee.trim().isEmpty() == false, "Net Command NCTB toBungee cannot be blank");
        this.toBungee = toBungee;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("to", toBungee);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

package com.rmb938.jedis.net.command.bungee;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandBTB extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandBTB.class.getName());

    private final String fromBungee;
    private final String toBungee;

    /**
     * A Command that goes from a bungee instance to another bungee instance
     * @param name - the name of the command
     * @param fromBungee - from bungee's internal IP address
     * @param toBungee - to bungee's internal IP address
     *                 If toBungee == * message is a broadcast to all bungee instances
     */
    public NetCommandBTB(String name, String fromBungee, String toBungee) {
        super(name, NetChannel.BUNGEE_TO_BUNGEE);
        Preconditions.checkNotNull(fromBungee, "Net Command BTB fromBungee cannot be null");
        Preconditions.checkNotNull(toBungee, "Net Command BTB toBungee cannot be null");
        Preconditions.checkArgument(fromBungee.trim().isEmpty() == false, "Net Command BTB fromBungee cannot be blank");
        Preconditions.checkArgument(toBungee.trim().isEmpty() == false, "Net Command BTB toBungee cannot be blank");
        this.fromBungee = fromBungee;
        this.toBungee = toBungee;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromBungee);
            getJsonObject().put("to", toBungee);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

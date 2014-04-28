package com.rmb938.jedis.net.command.bungee;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandBTSS extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandBTSS.class.getName());

    private final String fromBungee;

    /**
     * A Command that goes from a bungee instance to the social server
     * @param name - the name of the command
     * @param fromBungee - the bungee instance internal IP address
     */
    public NetCommandBTSS(String name, String fromBungee) {
        super(name, NetChannel.BUNGEE_TO_SERVER_CONTROLLER);
        Preconditions.checkNotNull(fromBungee, "Net Command BTSS fromBungee cannot be null");
        Preconditions.checkArgument(fromBungee.trim().isEmpty() == false, "Net Command BTSS fromBungee cannot be blank");
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

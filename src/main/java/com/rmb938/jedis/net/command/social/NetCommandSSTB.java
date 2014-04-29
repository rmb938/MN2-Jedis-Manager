package com.rmb938.jedis.net.command.social;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSSTB extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSSTB.class.getName());
    /**
     * A Command that goes from the social server to a server
     *
     * @param name - the name of the command
     */
    public NetCommandSSTB(String name) {
        super(name, NetChannel.SOCIAL_SERVER_TO_SERVER);
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", "socialServer");
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

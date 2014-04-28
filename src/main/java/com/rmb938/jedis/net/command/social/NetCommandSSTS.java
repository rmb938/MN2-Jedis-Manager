package com.rmb938.jedis.net.command.social;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSSTS  extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSSTS.class.getName());

    private final String toServerUUID;

    /**
     * A Command that goes from the social server to a server
     *
     * @param name - the name of the command
     * @param toServerUUID - serverUUID
     *                     If toServerUUID == * command is a broadcast to all servers
     *                     run by this controller
     */
    public NetCommandSSTS(String name, String toServerUUID) {
        super(name, NetChannel.SOCIAL_SERVER_TO_SERVER);
        Preconditions.checkNotNull(toServerUUID, "Net Command SSTS toServerPort cannot be null");
        Preconditions.checkArgument(toServerUUID.trim().isEmpty() == false, "Net Command SSTS toServerUUID cannot be empty");
        this.toServerUUID = toServerUUID;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("to", toServerUUID);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

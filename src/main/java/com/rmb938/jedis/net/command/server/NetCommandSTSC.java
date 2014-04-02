package com.rmb938.jedis.net.command.server;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSTSC extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSTSC.class.getName());

    private final int fromServerPort;

    /**
     * A Command that goes from a server to its server controller
     * @param name - name of the command
     * @param fromServerPort - serverName.port
     */
    public NetCommandSTSC(String name, int fromServerPort) {
        super(name, NetChannel.SERVER_TO_SERVER_CONTROLLER);
        Preconditions.checkNotNull(fromServerPort, "Net Command STSC fromServerPort cannot be null");
        Preconditions.checkArgument(fromServerPort > 0, "Net Command STSC fromServerPort must be greater than 0");
        this.fromServerPort = fromServerPort;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerPort);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

}

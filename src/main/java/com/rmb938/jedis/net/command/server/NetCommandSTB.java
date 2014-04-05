package com.rmb938.jedis.net.command.server;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSTB extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSTB.class.getName());

	private final String fromServerUUID;

    /**
     * A Command that goes from a Server to all bungee instances
     * @param name - command name
     * @param fromServerUUID - server UUID
     */
    public NetCommandSTB(String name, String fromServerUUID) {
		super(name, NetChannel.SERVER_TO_BUNGEE);
        Preconditions.checkNotNull(fromServerUUID, "Net Command STB fromServerUUID cannot be null");
        Preconditions.checkArgument(fromServerUUID.trim().isEmpty() == false, "Net Command STB fromServerUUID cannot be empty");
		this.fromServerUUID = fromServerUUID;
        buildJSON();
	}

	public void buildJSON() {
		try {
			getJsonObject().put("from", fromServerUUID);
		} catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
		}
	}
}

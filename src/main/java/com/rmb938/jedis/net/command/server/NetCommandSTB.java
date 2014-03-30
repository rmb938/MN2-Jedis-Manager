package com.rmb938.jedis.net.command.server;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSTB extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSTB.class.getName());

	private final String fromServerName;

    /**
     * A Command that goes from a Server to all bungee instances
     * @param name - command name
     * @param fromServerName - format serverName.port
     */
    public NetCommandSTB(String name, String fromServerName) {
		super(name, NetChannel.SERVER_TO_BUNGEE);
		this.fromServerName = fromServerName;
        buildJSON();
	}

	public void buildJSON() {
		try {
			getJsonObject().put("from", fromServerName);
		} catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
		}
	}
}

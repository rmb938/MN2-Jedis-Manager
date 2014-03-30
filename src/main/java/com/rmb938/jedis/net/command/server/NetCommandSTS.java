package com.rmb938.jedis.net.command.server;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSTS extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSTS.class.getName());

	private final String fromServerName;
	private final String toServerName;

    /**
     * A Command that goes from a server to one or many servers
     * @param name - command name
     * @param fromServerName - format serverName.port
     * @param toServerName - format serverName.port
     *                     If port == * command is a broadcast to all servers
     */
    public NetCommandSTS(String name, String fromServerName, String toServerName) {
		super(name, NetChannel.SERVER_TO_SERVER);
		this.fromServerName = fromServerName;
		this.toServerName = toServerName;
        buildJSON();
	}

	public void buildJSON() {
		try {
			getJsonObject().put("from", fromServerName);
            getJsonObject().put("to", toServerName);
		} catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
		}
	}
}

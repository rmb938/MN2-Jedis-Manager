package com.rmb938.jedis.net.command.bungee;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandBTS extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandBTS.class.getName());

    private final String fromBungee;
	private final String toServerName;

    /**
     * A Command that goes from a bungee instance to one or many servers
     * @param name - the name of the command
     * @param fromBungee - the internal bungee IP address
     * @param toServerName - format serverIP.serverName.port
     *                     If port == * command is a broadcast to all servers
     */
    public NetCommandBTS(String name, String fromBungee, String toServerName) {
		super(name, NetChannel.BUNGEE_TO_SERVER);
        Preconditions.checkNotNull(fromBungee, "Net Command BTS fromBungee cannot be null");
        Preconditions.checkNotNull(toServerName, "Net Command BTS toServerName cannot be null");
        Preconditions.checkArgument(fromBungee.trim().isEmpty() == false, "Net Command BTS fromBungee cannot be blank");
        Preconditions.checkArgument(toServerName.trim().isEmpty() == false, "Net Command BTS toServerName cannot be blank");
        this.fromBungee = fromBungee;
		this.toServerName = toServerName;
        buildJSON();
	}

	public void buildJSON() {
		try {
            getJsonObject().put("from", fromBungee);
			getJsonObject().put("to", toServerName);
		} catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
		}
	}
}

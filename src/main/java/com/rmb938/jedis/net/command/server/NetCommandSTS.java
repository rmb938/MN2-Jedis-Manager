package com.rmb938.jedis.net.command.server;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSTS extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSTS.class.getName());

	private final String fromServerUUID;
	private final String toServerUUID;

    /**
     * A Command that goes from a server to one or many servers
     * @param name - command name
     * @param fromServerUUID - server UUID
     * @param toServerUUID - server UUID
     *                     If toServerUUID == * command is a broadcast to all servers
     */
    public NetCommandSTS(String name, String fromServerUUID, String toServerUUID) {
		super(name, NetChannel.SERVER_TO_SERVER);
        Preconditions.checkNotNull(fromServerUUID, "Net Command STS fromServerUUID cannot be null");
        Preconditions.checkNotNull(toServerUUID, "Net Command STS toServerUUID cannot be null");
        Preconditions.checkArgument(fromServerUUID.trim().isEmpty() == false, "Net Command STS fromServerUUID cannot be empty");
        Preconditions.checkArgument(toServerUUID.trim().isEmpty() == false, "Net Command STS toServerUUID cannot be empty");
		this.fromServerUUID = fromServerUUID;
		this.toServerUUID = toServerUUID;
        buildJSON();
	}

	public void buildJSON() {
		try {
			getJsonObject().put("from", fromServerUUID);
            getJsonObject().put("to", toServerUUID);
		} catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
		}
	}
}

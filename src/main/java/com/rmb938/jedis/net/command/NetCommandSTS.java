package com.rmb938.jedis.net.command;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;
import org.json.JSONObject;

public class NetCommandSTS extends NetCommand {

	private final String fromServerName;
	private final String toServerName;

    public NetCommandSTS(String name, String fromServerName, String toServerName) {
		super(name, NetChannel.SERVER_TO_SERVER);
		this.fromServerName = fromServerName;
		this.toServerName = toServerName;
	}

	public JSONObject buildJSON() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("from", fromServerName);
			jsonObject.put("to", toServerName);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		this.addCommandInfo(jsonObject);
		return jsonObject;
	}
}

package com.rmb938.jedis.net.command;

import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;
import org.json.JSONObject;

public class NetCommandSTB extends NetCommand {

	private final String fromServerName;

    public NetCommandSTB(String name, String fromServerName) {
		super(name, NetChannel.SERVER_TO_BUNGEE);
		this.fromServerName = fromServerName;
	}

	public JSONObject buildJSON() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("from", fromServerName);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		this.addCommandInfo(jsonObject);
		return jsonObject;
	}
}

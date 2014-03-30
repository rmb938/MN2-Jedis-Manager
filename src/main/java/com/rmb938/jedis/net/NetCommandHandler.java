package com.rmb938.jedis.net;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class NetCommandHandler {

    private static HashMap<NetChannel, ArrayList<NetCommandHandler>> handlers = new HashMap<>();

    public static void addHandler(NetChannel netChannel, NetCommandHandler handler) {
        if (handlers.containsKey(netChannel)) {
            handlers.get(netChannel).add(handler);
        } else {
            ArrayList<NetCommandHandler> netCommandHandlers = new ArrayList<>();
            netCommandHandlers.add(handler);
            handlers.put(netChannel, netCommandHandlers);
        }
    }

    public static ArrayList<NetCommandHandler> getHandlers(NetChannel netChannel) {
        return handlers.get(netChannel);
    }

    public HashMap<String, Object> objectToHashMap(JSONObject data) throws JSONException {
        HashMap<String, Object> returnVal = new HashMap<>();
        Iterator i = data.keys();
        while (i.hasNext()) {
            Object next = i.next();
            if (!(next instanceof String)) continue;
            String key = (String)next;
            Object o = data.get(key);
            if (o instanceof JSONObject) {
                o = objectToHashMap((JSONObject) o);
            }
            returnVal.put(key, o);
        }
        return returnVal;
    }

    public abstract void handle(JSONObject jsonObject);
}

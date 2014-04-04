package com.rmb938.jedis.net.command.servercontroller;

import com.google.common.base.Preconditions;
import com.rmb938.jedis.net.NetChannel;
import com.rmb938.jedis.net.NetCommand;
import org.json.JSONException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NetCommandSCTB extends NetCommand {

    private final static Logger logger = Logger.getLogger(NetCommandSCTB.class.getName());

    private final String fromServerController;
    private final String toBungee;

    /**
     * A Command that goes from a Server Controller to its bungee instance
     * @param name - the name of the command
     * @param fromServerController - the server controller's internal IP address
     * @param toBungee - the bungee instance internal IP address
     *                 if toBungee == * message is broadcast to all bungee instances
     */
    public NetCommandSCTB(String name, String fromServerController, String toBungee) {
        super(name, NetChannel.SERVER_CONTROLLER_TO_BUNGEE);
        Preconditions.checkNotNull(fromServerController, "Net Command SCTB fromServerController cannot be null");
        Preconditions.checkArgument(fromServerController.trim().isEmpty() == false, "Net Command SCTB fromServerController cannot be empty");
        Preconditions.checkNotNull(toBungee, "Net Command SCTB toBungee cannot be null");
        Preconditions.checkArgument(toBungee.trim().isEmpty() == false, "Net Command SCTB toBungee cannot be empty");
        this.fromServerController = fromServerController;
        this.toBungee = toBungee;
        buildJSON();
    }

    public void buildJSON() {
        try {
            getJsonObject().put("from", fromServerController);
            getJsonObject().put("to", toBungee);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}

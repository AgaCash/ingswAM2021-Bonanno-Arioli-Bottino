package network.messages.gameMessages;

import network.messages.MessageType;

public class DevCardProductionResponse extends GameMessage{
    public DevCardProductionResponse (String username) {
        super(username, MessageType.PRODUCTIONUPDATE);
    }

}

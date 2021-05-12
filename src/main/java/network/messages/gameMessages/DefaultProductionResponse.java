package network.messages.gameMessages;

import network.messages.MessageType;

public class DefaultProductionResponse extends GameMessage {

    public DefaultProductionResponse(String username) {
        super(username, MessageType.PRODUCTIONUPDATE);
    }
}

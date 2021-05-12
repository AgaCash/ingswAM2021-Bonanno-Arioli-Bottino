package network.messages.gameMessages;

import network.messages.MessageType;

public class BuyDevCardResponse extends GameMessage{
    public BuyDevCardResponse(String username) {
        super(username, MessageType.MARKETUPDATE);
    }

}

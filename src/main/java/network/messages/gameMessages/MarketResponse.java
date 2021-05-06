package network.messages.gameMessages;

import network.messages.MessageType;

import java.util.ArrayList;

public class MarketResponse extends GameMessage{
    public MarketResponse(String username) {
        super(username, MessageType.MARKETUPDATE);
    }

}

package network.messages.gameMessages;

import network.messages.MessageType;

public class BuyResourcesResponse extends GameMessage{

    public BuyResourcesResponse(String username){
        super(username, MessageType.MARKET);
    }

    public void executeCommand(){

    }

}

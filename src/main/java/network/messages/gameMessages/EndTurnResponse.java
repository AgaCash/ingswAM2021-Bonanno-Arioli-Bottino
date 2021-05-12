package network.messages.gameMessages;

import network.messages.MessageType;

public class EndTurnResponse extends GameMessage{

    public EndTurnResponse(String username){
        super(username, MessageType.ENDTURNUPDATE);
    }
}

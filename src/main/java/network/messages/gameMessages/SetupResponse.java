package network.messages.gameMessages;

import network.messages.MessageType;

public class SetupResponse extends GameMessage {
    private String message;

    public SetupResponse(String username){
        super(username, MessageType.SETUPRESPONSE);
        this.message = "bella frate";
    }
}

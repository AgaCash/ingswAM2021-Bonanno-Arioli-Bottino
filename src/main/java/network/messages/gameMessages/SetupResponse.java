package network.messages.gameMessages;

import network.messages.MessageType;

public class SetupResponse extends GameMessage {
    private String message;
    private String firstPlayer;

    public SetupResponse(String username, String firstPlayer){
        super(username, MessageType.SETUPRESPONSE);
        this.message = "bella frate";
    }

    public void executeCommand(){
        //
    }
}

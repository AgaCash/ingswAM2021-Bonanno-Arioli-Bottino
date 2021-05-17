package network.messages.gameMessages;

import network.messages.MessageType;

public class InternalErrorNotify extends GameMessage{
    private String message;

    public InternalErrorNotify(String username, String message){
        super(username, MessageType.FAILEDACTIONNOTIFY);
        this.message = message;
    }
}

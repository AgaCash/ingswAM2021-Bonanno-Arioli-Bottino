package network.messages.gameMessages;

import network.messages.MessageType;

public class FailedActionNotify extends GameMessage {
    private String message;

    public FailedActionNotify(String username, String message){
        super(username, MessageType.FAILEDACTIONNOTIFY);
        this.message = message;
    }
}

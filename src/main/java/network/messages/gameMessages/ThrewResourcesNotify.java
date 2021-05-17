package network.messages.gameMessages;

import network.messages.MessageType;

public class ThrewResourcesNotify extends GameMessage {
    private String message;

    public ThrewResourcesNotify(String username, String message){
        super(username, MessageType.FAILEDACTIONNOTIFY);
        this.message = message;
    }
}

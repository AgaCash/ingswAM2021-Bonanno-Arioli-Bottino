package network.messages.notifies;

import network.messages.Message;
import network.messages.MessageType;

public abstract class NotifyMessage extends Message {
    private String username;
    private String message;

    public NotifyMessage(String username, MessageType messageType, String message){
        super(username, messageType);
        this.message = message;
    }
}

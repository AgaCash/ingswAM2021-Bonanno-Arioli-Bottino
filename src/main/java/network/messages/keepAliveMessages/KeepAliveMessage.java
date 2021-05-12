package network.messages.keepAliveMessages;

import network.messages.Message;
import network.messages.MessageType;

public abstract class KeepAliveMessage extends Message {
    public KeepAliveMessage(String username, MessageType messageType) {
        super(username, messageType);
    }

    public void executeCommand(){

    }
}

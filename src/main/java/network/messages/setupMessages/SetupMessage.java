package network.messages.setupMessages;

import network.messages.Message;
import network.messages.MessageType;

public abstract class SetupMessage extends Message {
    public SetupMessage(String username, MessageType messageType) {
        super(username, messageType);
    }
    /*
    public void executeCommand(ConnectionHandler connectionHandler, View view) {
        System.out.println("default executeCommand SetupMessage");
    }*/
}

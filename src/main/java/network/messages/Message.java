package network.messages;

import controller.Controller;
import network.server.LobbyHandler;

public abstract class Message {
    private String username;
    private MessageType messageType;

    public Message(String username, MessageType messageType) {
        this.username = username;
        this.messageType = messageType;
    }

    public String getUsername() {
        return username;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}

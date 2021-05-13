package network.messages.setupMessages;

import network.messages.*;
import network.server.ConnectionHandler;

import java.io.PrintWriter;

public class CheckUsernameRequest extends SetupMessage{
    public CheckUsernameRequest(String username) {
        super(username, MessageType.CHECK_USERNAME_REQUEST);
    }

    /*
    @Override
    public void executeCommand(ConnectionHandler connectionHandler, View view) {
        connectionHandler.checkUsername(super.getUsername());
    }*/
}

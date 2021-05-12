package network.messages.gameMessages;

import controller.Controller;
import network.messages.Message;
import network.messages.MessageType;

import java.io.PrintWriter;

public abstract class GameMessage extends Message {
    public GameMessage(String username, MessageType messageType) {
        super(username, messageType);
    }

    public void executeCommand(Controller controller, PrintWriter out){
        //System.out.println("default executeCommand CONTROLLER");
    }


}

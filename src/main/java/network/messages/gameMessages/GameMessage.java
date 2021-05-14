package network.messages.gameMessages;

import controller.Controller;
import network.messages.Message;
import network.messages.MessageType;
import view.View;

import view.View;
import view.*;

import java.util.ArrayList;

public abstract class GameMessage extends Message {
    public GameMessage(String username, MessageType messageType) {
        super(username, messageType);
    }

    public void executeCommand(Controller controller, View view){
        //System.out.println("default executeCommand CONTROLLER");
    }

    public void executeCommand(Controller controller, ArrayList<VirtualClient> views){
        //QUESTO SARA' ESEGUITO DAL SERVER
    }




}

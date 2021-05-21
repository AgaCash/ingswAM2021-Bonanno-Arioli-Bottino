package network.messages.gameMessages;

import clientController.LightController;
import controller.Controller;
import network.messages.Message;
import network.messages.MessageType;
import view.VirtualClient;

public abstract class GameMessage extends Message {
    public GameMessage(String username, MessageType messageType) {
        super(username, messageType);
    }

    public void executeCommand(LightController controller){
        //System.out.println("default executeCommand CONTROLLER");
    }

    public void executeCommand(Controller controller, VirtualClient client){
        //QUESTO SARA' ESEGUITO DAL SERVER
    }




}

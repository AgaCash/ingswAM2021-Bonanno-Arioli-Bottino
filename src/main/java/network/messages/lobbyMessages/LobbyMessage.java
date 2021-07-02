package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.Message;
import network.messages.MessageType;
import view.VirtualClient;


public abstract class LobbyMessage extends Message {
    public LobbyMessage(String username, MessageType messageType) {
        super(username, messageType);
    }


    public void executeCommand(VirtualClient virtualClient) {
        System.out.println("default executeCommand LOBBY");
        //STESSO DISCORSO DI GAMEMESSAGE
    }

    public void executeCommand(LightController lightController){
        //SARA' ESEGUITO DAL CLIENT
    }
}

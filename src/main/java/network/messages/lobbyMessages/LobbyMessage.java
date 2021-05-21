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
        //
        //STESSO DISCORSO DI GAMEMESSAGE MA FORSE QUI NON SERVE
    }

    public void executeCommand(LightController lightController){
        //SARA' ESEGUITO DAL CLIENT
        //  COLUI CHE CAMBIERA' LO STATO DELL'AUTOMA DEL123.
        //  CLIENT
    }
}

package network.messages.lobbyMessages;

import network.messages.Message;
import network.messages.MessageType;
import network.server.LobbyHandler;
import view.View;
import view.VirtualClient;


public abstract class LobbyMessage extends Message {
    public LobbyMessage(String username, MessageType messageType) {
        super(username, messageType);
    }


    public void executeCommand(LobbyHandler lobbyHandler,  VirtualClient virtualClient) {
        System.out.println("default executeCommand LOBBY");
        //
        //STESSO DISCORSO DI GAMEMESSAGE MA FORSE QUI NON SERVE
    }
}

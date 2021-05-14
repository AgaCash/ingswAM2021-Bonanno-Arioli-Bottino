package network.messages.lobbyMessages;

import network.messages.MessageType;
import network.messages.notifies.FailedActionNotify;
import network.server.LobbyHandler;
import model.player.Player;
import view.View;
import view.VirtualClient;
import view.*;

import javax.naming.SizeLimitExceededException;

public class CreateLobbyRequest extends LobbyMessage{


    public CreateLobbyRequest(String username) {
        super(username, MessageType.CREATEMULTIPLAYER);
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, VirtualClient virtualClient) {
        try {
            lobbyHandler.createLobby(new Player(super.getUsername()), virtualClient);
            StandardLobbyResponse s = new StandardLobbyResponse(getUsername(), true);
            virtualClient.getVirtualView().sendLobbyMessage(s);
        }catch (SizeLimitExceededException s){
            //non entrerà mai qua
        }
    }
}

package network.messages.lobbyMessages;

import model.player.Player;
import network.messages.MessageType;
import network.server.LobbyHandler;
import view.VirtualClient;

import javax.naming.SizeLimitExceededException;

public class CreateLobbyRequest extends LobbyMessage{


    public CreateLobbyRequest(String username) {
        super(username, MessageType.CREATEMULTIPLAYER);
    }

    @Override
    public void executeCommand(VirtualClient virtualClient) {
        try {
            LobbyHandler.getInstance().createLobby(new Player(super.getUsername()), virtualClient);
            StandardLobbyResponse s = new StandardLobbyResponse(getUsername(), true);
            virtualClient.getVirtualView().sendLobbyMessage(s);
        }catch (SizeLimitExceededException s){
            //non entrerà mai qua
            s.printStackTrace();
        }
    }
}

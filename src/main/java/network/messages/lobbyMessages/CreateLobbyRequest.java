package network.messages.lobbyMessages;

import com.google.gson.Gson;
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
            CreateLobbyResponse s = new CreateLobbyResponse(getUsername());
            virtualClient.getVirtualView().sendLobbyResponse(s);
        }catch (SizeLimitExceededException s){
            s.printStackTrace();
        }
    }
}

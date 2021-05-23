package network.messages.lobbyMessages;

import controller.Controller;
import network.messages.MessageType;
import network.server.LobbyHandler;
import model.player.Player;
import view.VirtualClient;

public class StartSinglePlayerRequest extends LobbyMessage{

    public StartSinglePlayerRequest(String username) {
        super(username, MessageType.JOIN_SINGLEPLAYER);
    }

    @Override
    public void executeCommand(VirtualClient virtualClient) {
        LobbyHandler.getInstance().createSinglePlayer(new Player(super.getUsername()), virtualClient);
        //TODO:
        //  getData
        //  updateToView
        StartSinglePlayerResponse response = new StartSinglePlayerResponse(getUsername());
        virtualClient.getVirtualView().sendLobbyResponse(response);
    }

    @Override
    public String toString() {
        return "LoginSinglePlayerRequest{" +
                "username=" + super.getUsername() +
                '}';
    }
}

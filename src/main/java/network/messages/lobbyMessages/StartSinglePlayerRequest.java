package network.messages.lobbyMessages;

import controller.Controller;
import network.messages.MessageType;
import network.server.LobbyHandler;
import model.player.Player;
import view.View;
import view.VirtualClient;
import view.*;

public class StartSinglePlayerRequest extends LobbyMessage{

    public StartSinglePlayerRequest(String username) {
        super(username, MessageType.JOIN_SINGLEPLAYER);
    }

    @Override
    public void executeCommand(VirtualClient virtualClient) {
        Controller c = LobbyHandler.getInstance().createSinglePlayer(new Player(super.getUsername()), virtualClient);
        virtualClient.setController(c);
        //TODO:
        //  getData
        //  updateToView
        virtualClient.getVirtualView().updateStartingSituation();
    }

    @Override
    public String toString() {
        return "LoginSinglePlayerRequest{" +
                "username=" + super.getUsername() +
                '}';
    }
}

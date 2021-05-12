package network.messages.lobbyMessages;

import controller.Controller;
import network.messages.MessageType;
import network.server.LobbyHandler;
import model.Player;
import view.View;
import view.VirtualClient;
import view.VirtualView;

public class StartSinglePlayerRequest extends LobbyMessage{

    public StartSinglePlayerRequest(String username) {
        super(username, MessageType.JOIN_SINGLEPLAYER);
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, View view, VirtualClient virtualClient) {
        VirtualView virtualView = (VirtualView) view;
        Controller c = lobbyHandler.createSinglePlayer(new Player(super.getUsername()));
        virtualClient.setController(c);
        //TODO:
        //  getData
        //  updateToView
    }

    @Override
    public String toString() {
        return "LoginSinglePlayerRequest{" +
                "username=" + super.getUsername() +
                '}';
    }
}

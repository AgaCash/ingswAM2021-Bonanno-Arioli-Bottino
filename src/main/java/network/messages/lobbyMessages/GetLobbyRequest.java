package network.messages.lobbyMessages;

import network.messages.MessageType;
import network.server.Lobby;
import network.server.LobbyHandler;
import view.View;
import view.VirtualClient;
import view.*;
import java.util.ArrayList;

public class GetLobbyRequest extends LobbyMessage{

    public GetLobbyRequest(String nickname) {
        super(nickname, MessageType.GETLOBBIES);
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler,  VirtualClient virtualClient) {
        ArrayList<Lobby> lobbies = lobbyHandler.getLobbies();
        //System.out.println("LOBBIES: "+lobbies);
        GetLobbyResponse response = new GetLobbyResponse(super.getUsername(),lobbies);
        System.out.println(response);
        virtualClient.getVirtualView().sendGetLobby(response);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

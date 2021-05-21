package network.messages.lobbyMessages;

import com.google.gson.Gson;
import model.player.Player;
import network.messages.MessageType;
import network.server.Lobby;
import network.server.LobbyHandler;

import java.io.IOException;
import java.net.Socket;
import view.VirtualClient;
import view.*;
import java.util.ArrayList;

public class GetLobbyRequest extends LobbyMessage{

    public GetLobbyRequest(String nickname) {
        super(nickname, MessageType.GETLOBBIES);
    }

    //  TODO:
    //      da togliere lobbyHandler come parametro e passargli il getInstance?
    @Override
    public void executeCommand(VirtualClient virtualClient) {
        ArrayList<Lobby> l = LobbyHandler.getInstance().getLobbies();
        GetLobbyResponse g = new GetLobbyResponse(super.getUsername(), l);
        System.out.println("object to serialize: "+g);
        virtualClient.getVirtualView().sendLobbyResponse(g);
    }

}

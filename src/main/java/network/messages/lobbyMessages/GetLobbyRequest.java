package network.messages.lobbyMessages;

import com.google.gson.Gson;
import network.messages.MessageType;
import network.server.Lobby;
import network.server.LobbyHandler;

import java.io.PrintWriter;
import java.util.ArrayList;

public class GetLobbyRequest extends LobbyMessage{

    public GetLobbyRequest(String nickname) {
        super(nickname, MessageType.GETLOBBIES);
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, PrintWriter out) {
        Gson gson = new Gson();
        System.out.println("SON dentro?");
        ArrayList<Lobby> lobbies = lobbyHandler.getLobbies();
        System.out.println("LOBBIES: "+lobbies);
        GetLobbyResponse response = new GetLobbyResponse(this.getUsername(),lobbies);
        out.println( gson.toJson(response) );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

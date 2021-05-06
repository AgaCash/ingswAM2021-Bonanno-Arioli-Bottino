package network.messages.lobbyMessages;

import network.messages.MessageType;
import network.server.Lobby;
import network.server.LobbyHandler;

import java.io.PrintWriter;
import java.util.ArrayList;

public class GetLobbyResponse extends LobbyMessage{
    private ArrayList<Lobby> lobbies;

    public GetLobbyResponse(String nickname, ArrayList<Lobby> lobbies) {
        super(nickname, MessageType.LOBBYLISTRESPONSE);
        this.lobbies = lobbies;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, PrintWriter out) {
        System.out.println("Sono QUA?");
    }

    @Override
    public String toString() {
        return "LobbyListRequest{" +
                "lobbies=" + lobbies +
                '}';
    }
}

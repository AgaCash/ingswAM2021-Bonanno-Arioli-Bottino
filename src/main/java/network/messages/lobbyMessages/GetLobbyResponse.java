package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;
import network.server.Lobby;

import java.util.ArrayList;

public class GetLobbyResponse extends LobbyMessage{
    private ArrayList<Lobby> lobbies;
    private boolean success;
    private String message;

    public GetLobbyResponse(String nickname, ArrayList<Lobby> lobbies) {
        super(nickname, MessageType.GETLOBBIES_RESPONSE);
        this.lobbies = lobbies;
        success = true;
    }

    public GetLobbyResponse(String nickname, String message) {
        super(nickname, MessageType.GETLOBBIES_RESPONSE);
        this.message = message;
        success = false;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    @Override
    public void executeCommand(LightController lightController) {
        //lightController.showLobbies(lobbies);
    }




    @Override
    public String toString() {
        return "{" +
                "\"lobbies\":" + lobbies +
                '}';
    }
}

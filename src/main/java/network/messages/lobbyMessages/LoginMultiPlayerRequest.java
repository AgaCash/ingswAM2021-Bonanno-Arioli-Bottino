package network.messages.lobbyMessages;

import exceptions.LobbyFullException;
import network.messages.MessageType;
import network.server.LobbyHandler;
import model.player.Player;
import view.View;
import view.VirtualClient;
import view.*;

public class LoginMultiPlayerRequest extends LobbyMessage{
    private int lobbyId;

    public LoginMultiPlayerRequest(String username, int lobbyId) {
        super(username, MessageType.JOINMULTIPLAYER);
        this.lobbyId = lobbyId;
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler,  VirtualClient virtualClient) {
        StandardLobbyResponse s = null;
        try{
            lobbyHandler.joinLobby(new Player(this.getUsername()), this.lobbyId, virtualClient);
            s = new StandardLobbyResponse(getUsername(), true);
        }catch (LobbyFullException l){
            s = new StandardLobbyResponse(getUsername(), false, "Lobby selected is full");
        }catch (IndexOutOfBoundsException i){
            s = new StandardLobbyResponse(getUsername(), false, "Lobby selected doesn't exist");
        }finally {
            virtualClient.getVirtualView().sendStandardLobbyResponse(s);
        }
    }

    @Override
    public String toString() {
        return "LoginMultiPlayerRequest{" +
                "lobbyId=" + lobbyId +
                '}';
    }
}

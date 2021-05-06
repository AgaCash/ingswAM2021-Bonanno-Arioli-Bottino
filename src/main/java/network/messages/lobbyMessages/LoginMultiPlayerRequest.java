package network.messages.lobbyMessages;

import network.messages.MessageType;
import network.server.LobbyHandler;
import network.server.Player;

import javax.naming.SizeLimitExceededException;
import java.io.PrintWriter;

public class LoginMultiPlayerRequest extends LobbyMessage{
    private int lobbyId;

    public LoginMultiPlayerRequest(String username, int lobbyId) {
        super(username, MessageType.JOINMULTIPLAYER);
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, PrintWriter out) {
        try{
            lobbyHandler.joinLobby(new Player(this.getUsername()), this.lobbyId);
        }catch (SizeLimitExceededException s){

        }
    }

    @Override
    public String toString() {
        return "LoginMultiPlayerRequest{" +
                "lobbyId=" + lobbyId +
                '}';
    }
}

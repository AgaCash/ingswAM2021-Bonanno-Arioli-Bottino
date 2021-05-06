package network.messages.lobbyMessages;

import network.messages.MessageType;
import network.server.LobbyHandler;

import java.io.PrintWriter;

public class GetLobbyRequest extends LobbyMessage{

    public GetLobbyRequest(String nickname) {
        super(nickname, MessageType.LOBBYLISTRESPONSE);
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, PrintWriter out) {
        lobbyHandler = LobbyHandler.getInstance();
        lobbyHandler.getLobbies();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

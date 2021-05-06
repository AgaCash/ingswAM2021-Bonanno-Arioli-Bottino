package network.messages.lobbyMessages;

import network.messages.MessageType;

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
    public String toString() {
        return "LoginMultiPlayerRequest{" +
                "lobbyId=" + lobbyId +
                '}';
    }
}

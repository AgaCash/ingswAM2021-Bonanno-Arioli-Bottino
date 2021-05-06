package network.messages.lobbyMessages;

import network.messages.MessageType;

public class LoginSinglePlayerRequest extends LobbyMessage{

    public LoginSinglePlayerRequest(String username) {
        super(username, MessageType.JOIN_SINGLEPLAYER);
    }

    @Override
    public String toString() {
        return "LoginSinglePlayerRequest{" +
                "username=" + super.getUsername() +
                '}';
    }
}

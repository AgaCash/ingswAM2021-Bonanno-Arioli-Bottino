package network.messages.lobbyMessages;

import network.messages.MessageType;

public class StartMultiPlayerResponse extends LobbyMessage{
    public StartMultiPlayerResponse(String username) {
        super(username, MessageType.LOBBYSTARTGAME_RESPONSE);
    }
}

package network.messages.pingMessages;

import network.messages.MessageType;
import network.messages.lobbyMessages.LobbyMessage;
import view.VirtualClient;

public class PongLobbyMessage extends LobbyMessage {
    public PongLobbyMessage(String username) {
        super(username, MessageType.PONG_LOBBY);
    }

    @Override
    public void executeCommand(VirtualClient virtualClient) {
        virtualClient.resetLobbyTimer();
    }
}

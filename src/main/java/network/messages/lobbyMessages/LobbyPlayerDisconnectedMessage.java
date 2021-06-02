package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;

public class LobbyPlayerDisconnectedMessage extends LobbyMessage {
    public LobbyPlayerDisconnectedMessage(String username) {
        super(username, MessageType.LOBBY_PLAYER_DISCONNECTED);
    }

    @Override
    public void executeCommand(LightController controller) {
        controller.notifyPlayerDisconnected(getUsername());
    }
}

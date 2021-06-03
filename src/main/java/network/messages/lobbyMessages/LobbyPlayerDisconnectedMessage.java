package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;

public class LobbyPlayerDisconnectedMessage extends LobbyMessage {
    private boolean creatorDisconnected;

    public LobbyPlayerDisconnectedMessage(String username, boolean creatorDisconnected) {
        super(username, MessageType.LOBBY_PLAYER_DISCONNECTED);
        this.creatorDisconnected = creatorDisconnected;
    }

    @Override
    public void executeCommand(LightController controller) {
        if(creatorDisconnected)
            controller.notifyCreatorDisconnected();
        else
            controller.notifyPlayerDisconnected(getUsername());
    }
}

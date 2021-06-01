package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

public class PlayerDisconnectedMessage extends GameMessage {
    public PlayerDisconnectedMessage(String username) {
        super(username, MessageType.PLAYER_DISCONNECTED);
    }

    @Override
    public void executeCommand(LightController controller) {
        controller.notifyPlayerDisconnected(getUsername());
    }
}

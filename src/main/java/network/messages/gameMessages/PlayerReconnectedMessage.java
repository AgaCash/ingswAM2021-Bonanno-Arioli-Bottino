package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;
//todo aga
public class PlayerReconnectedMessage extends GameMessage{

    public PlayerReconnectedMessage(String username) {
        super(username, MessageType.PLAYER_RECONNECTED);
    }

    @Override
    public void executeCommand(LightController controller) {
        controller.notifyPlayerReconnected(getUsername());
    }
}

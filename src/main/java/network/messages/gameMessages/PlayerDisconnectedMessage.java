package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

/**
 * Class that represents the message sent by server when a player left the game
 */
public class PlayerDisconnectedMessage extends GameMessage {
    /**
     * Default constructor of the message
     * @param username disconnected player's username
     */
    public PlayerDisconnectedMessage(String username) {
        super(username, MessageType.PLAYER_DISCONNECTED);
    }

    /**
     * Code that will be executed by the client:
     * notify user that player with this username left the game
     * @param controller the LightController in Client
     */
    @Override
    public void executeCommand(LightController controller) {
        controller.notifyPlayerDisconnected(getUsername());
    }
}

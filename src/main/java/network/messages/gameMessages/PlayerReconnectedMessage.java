package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

/**
 * Class that represents the message sent by server when a player rejoin the game
 */
public class PlayerReconnectedMessage extends GameMessage{
    /**
     * Default constructor specifying the player's username reconnected
     * @param username username of the player reconnected
     */
    public PlayerReconnectedMessage(String username) {
        super(username, MessageType.PLAYER_RECONNECTED);
    }

    /**
     * Code that will be executed by client:
     * notify user that player with this username reconnected
     * @param controller the LightController in Client
     */
    @Override
    public void executeCommand(LightController controller) {
        controller.notifyPlayerReconnected(getUsername());
    }
}

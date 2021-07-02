package network.messages.pingMessages;

import controller.Controller;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;
import view.VirtualClient;

//PONG STARTS FROM THE CLIENT
public class PongGameMessage extends GameMessage {
    public PongGameMessage(String username) {
        super(username, MessageType.PONG_GAME);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        client.resetGameTimer();
    }
}

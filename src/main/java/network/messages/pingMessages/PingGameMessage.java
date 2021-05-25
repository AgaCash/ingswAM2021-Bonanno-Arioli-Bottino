package network.messages.pingMessages;

import clientController.LightController;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;

//PING PARTE DAL SERVER
public class PingGameMessage extends GameMessage {
    public PingGameMessage(String username) {
        super(username, MessageType.PING_GAME);
    }

    @Override
    public void executeCommand(LightController controller) {
        System.out.println("PING_GAME");
        controller.sendGamePong();
    }
}

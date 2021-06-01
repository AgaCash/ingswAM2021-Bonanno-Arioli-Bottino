package network.messages.pingMessages;

import clientController.LightController;
import network.messages.MessageType;
import network.messages.lobbyMessages.LobbyMessage;

public class PingLobbyMessage extends LobbyMessage {
    public PingLobbyMessage(String username) {
        super(username, MessageType.PING_LOBBY);
    }

    @Override
    public void executeCommand(LightController lightController) {
        //System.out.println("PING_LOBBY");
        lightController.sendLobbyPong();
    }
}

package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;

public class StartSinglePlayerResponse extends LobbyMessage{
    private boolean success;
    private String message;

    public StartSinglePlayerResponse(String username) {
        super(username, MessageType.JOIN_SINGLEPLAYER_RESPONSE);
        success = true;
    }

    public StartSinglePlayerResponse(String username, String message) {
        super(username, MessageType.JOIN_SINGLEPLAYER_RESPONSE);
        success = false;
        this.message = message;
    }

    @Override
    public void executeCommand(LightController lightController) {
        if(success){
            //fa partire la partita singleplayer
            lightController.startSinglePlayerGame();
        }else{
            lightController.showError(getUsername(), message);
        }
    }
}

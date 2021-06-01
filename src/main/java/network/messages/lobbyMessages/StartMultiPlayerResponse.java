package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;

public class StartMultiPlayerResponse extends LobbyMessage{
    private boolean success;
    private String message;

    public StartMultiPlayerResponse(String username) {
        super(username, MessageType.LOBBYSTARTGAME_RESPONSE);
        success = true;
    }

    public StartMultiPlayerResponse(String username, String message) {
        super(username, MessageType.LOBBYSTARTGAME_RESPONSE);
        success = false;
        this.message = message;
    }

    @Override
    public void executeCommand(LightController lightController) {
        if(success){
            //chiama un metodo di controller che "cambia la schermata" e passa al gioco
            lightController.startMultiPlayerGame();
        }else{
            lightController.showError(getUsername(), message);
            lightController.waitStartGameString();
        }
    }

    public boolean isSuccess() {
        return success;
    }

}

package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;

public class LoginMultiPlayerResponse extends LobbyMessage{
    private boolean status;
    private String message;

    public LoginMultiPlayerResponse(String username) {
        super(username, MessageType.JOINMULTIPLAYER_RESPONSE);
        status = true;
    }

    public LoginMultiPlayerResponse(String username, String message) {
        super(username, MessageType.JOINMULTIPLAYER_RESPONSE);
        status = false;
        this.message = message;
    }

    @Override
    public void executeCommand(LightController lightController) {
        if(status){
            // deve entrare nella fase di attesa di altri giocatori
            //lightController.joinLobbyWaiting();
        }else{
            //lightController.showError(message);
        }
    }
}

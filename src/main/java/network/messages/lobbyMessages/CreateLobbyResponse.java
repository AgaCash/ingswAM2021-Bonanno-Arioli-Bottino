package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;

public class CreateLobbyResponse extends LobbyMessage{
    private boolean success;
    private String message;


    public CreateLobbyResponse(String username, String message) {
        super(username,MessageType.CREATEMULTIPLAYER_RESPONSE);
        this.success = false;
        this.message = message;
    }

    public CreateLobbyResponse(String username) {
        super(username,MessageType.CREATEMULTIPLAYER_RESPONSE);
        this.success = true;
        this.message = null;
    }

    @Override
    public void executeCommand(LightController lightController) {
        if(success){
            // deve entrare nella fase di attesa di altri giocatori DA CREATORE(con possibilità di startare il game)
            lightController.createLobbyWaiting();
        }else{
            //mostra l'errore a schermo
            lightController.showError(getUsername(),message);
        }
    }
}

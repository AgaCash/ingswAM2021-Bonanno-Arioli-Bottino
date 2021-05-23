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

    //TODO:
    //  SISTEMARE L'INIZIO PARTITA COMANDATO DAL LOBBY_CREATOR
    //  invece di iniziare la partita mi rimostra le lobby
    @Override
    public void executeCommand(LightController lightController) {
        if(status){
            // deve entrare nella fase di attesa di altri giocatori
            if(getUsername().equals(lightController.getUsername())){
                lightController.joinLobbyWaiting();
            }else{
                //deve solo notificare che è entrato
                lightController.notifyPlayerJoined(getUsername());
            }
        }else{
            lightController.showError(getUsername() ,message);
            lightController.getLobbyList();
        }
    }
}

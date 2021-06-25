package network.messages.lobbyMessages;

import clientController.LightController;
import exceptions.NoSuchUsernameException;
import network.messages.MessageType;
import network.server.LobbyHandler;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class LoginMultiPlayerResponse extends LobbyMessage{
    private boolean success;
    private String message;
    private ArrayList<String> usernameList;

    public LoginMultiPlayerResponse(String username, ArrayList<String> usernameList) {
        super(username, MessageType.JOINMULTIPLAYER_RESPONSE);
        success = true;
        this.usernameList = usernameList;
    }

    public LoginMultiPlayerResponse(String username, String message) {
        super(username, MessageType.JOINMULTIPLAYER_RESPONSE);
        success = false;
        this.message = message;
        usernameList = null;
    }

    @Override
    public void executeCommand(LightController lightController) {
        if(success){
            // deve entrare nella fase di attesa di altri giocatori
            if(getUsername().equals(lightController.getUsername())){
                lightController.joinLobbyWaiting(usernameList);
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

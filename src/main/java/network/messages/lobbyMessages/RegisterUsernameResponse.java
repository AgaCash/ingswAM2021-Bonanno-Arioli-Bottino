package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;

public class RegisterUsernameResponse extends LobbyMessage{
    private boolean success;
    private String message;

    public RegisterUsernameResponse(String username) {
        super(username, MessageType.REGISTER_USERNAME_RESPONSE);
        success = true;
    }

    public RegisterUsernameResponse(String username, String message) {
        super(username, MessageType.REGISTER_USERNAME_RESPONSE);
        success = false;
        this.message = message;
    }

    @Override
    public void executeCommand(LightController lightController) {
        if(success){
            //deve mostrargli il menù
            lightController.askLobbyMenu();
        }else{
            lightController.showError(getUsername(), message);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

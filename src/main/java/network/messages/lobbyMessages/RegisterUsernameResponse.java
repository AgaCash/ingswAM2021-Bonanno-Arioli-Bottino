package network.messages.lobbyMessages;

import clientController.LightController;
import network.messages.MessageType;

public class RegisterUsernameResponse extends LobbyMessage{
    private boolean success;
    private String message;
    private boolean reconnection;

    public RegisterUsernameResponse(String username, boolean reconnection) {
        super(username, MessageType.REGISTER_USERNAME_RESPONSE);
        success = true;
        this.reconnection = reconnection;
    }

    public RegisterUsernameResponse(String username, String message) {
        super(username, MessageType.REGISTER_USERNAME_RESPONSE);
        success = false;
        this.message = message;
        this.reconnection = false;
    }

    @Override
    public void executeCommand(LightController lightController) {
        if(success){
            if(reconnection){
                //System.out.println("SEI TORNATO IN GAME");
                lightController.reconnectToGame();
            }else {
                //deve mostrargli il menù
                lightController.askLobbyMenu();
            }
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

package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

public class LeaderCardActivationResponse extends GameMessage{
    private boolean success;
    private String message;

    public LeaderCardActivationResponse(String username) {
        super(username, MessageType.LEADERCARDUPDATE);
        this.success = true;
        this.message = "LeaderCard successfully activated";
        System.out.println("LeaderCard successfully activated");
    }

    public LeaderCardActivationResponse(String username, String message){
        super(username, MessageType.LEADERCARDUPDATE);
        this.message = message;
        this.success = false;
        System.out.println(message);
    }

    @Override
    public void executeCommand(LightController controller){
        if(this.success){
            controller.showSuccess(getUsername(), message);
            //todo: è nei messaggi che viene deciso cosa può fare dopo l'utente
        }
        else{
            controller.showError(getUsername(), message);
        }
    }


}

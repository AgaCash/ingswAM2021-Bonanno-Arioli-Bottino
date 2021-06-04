package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import network.messages.MessageType;

import java.util.ArrayList;

public class LeaderCardActivationResponse extends GameMessage{
    private boolean success;
    private String message;
    private ArrayList<LightLeaderCard> newSlot;

    public LeaderCardActivationResponse(String username, ArrayList<LightLeaderCard> newSlot) {
        super(username, MessageType.LEADERCARDUPDATE);
        this.success = true;
        this.message = "LeaderCard successfully activated";
        this.newSlot = newSlot;
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
            controller.updateLeaderSlot(getUsername(), this.newSlot);
            controller.showSuccess(message);
            //todo: è nei messaggi che viene deciso cosa può fare dopo l'utente
        }
        else{
            controller.showError(getUsername(), message);
        }
    }


}

package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import network.messages.MessageType;

import java.util.ArrayList;

public class LeaderCardThrowResponse extends GameMessage{
    private boolean success;
    private String message;
    private int position;
    private ArrayList<LightLeaderCard> newSlot;

    public LeaderCardThrowResponse(String username, int position, ArrayList<LightLeaderCard> newSlot) {
        super(username, MessageType.LEADERCARDTHROWUPDATE);
        this.success = true;
        this.position = position;
        this.newSlot = newSlot;
    }

    public LeaderCardThrowResponse(String username, String message){
        super(username, MessageType.LEADERCARDUPDATE);
        this.message = message;
        this.success = false;
    }

    @Override
    public void executeCommand(LightController controller){
        if(this.success){
            controller.updateLeaderSlot(getUsername(), this.newSlot);
            if(controller.getUsername().equals(getUsername())) {
                controller.getPlayerBoard().getFaithTrack().setCurrentPos(position);
                controller.showSuccess(getUsername(), "successful throwing!\n+++ you earned a faith point!");
            }
            else
                showUpdates(controller);

        }
        else{
            controller.showError(getUsername(), message);
        }
    }

    private void showUpdates(LightController controller){
        controller.showOthersActions(getUsername(), " has threw a leader card");
        controller.showOthersActions(getUsername(), " leader slots after throwing: "+newSlot.toString());
    }
}

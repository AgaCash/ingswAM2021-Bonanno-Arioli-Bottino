package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import clientModel.table.LightFaithTrack;
import network.messages.MessageType;

import java.util.ArrayList;

public class LeaderCardThrowResponse extends GameMessage{
    private boolean success;
    private String message;
    private LightFaithTrack track;
    private ArrayList<LightLeaderCard> newSlot;

    public LeaderCardThrowResponse(String username, LightFaithTrack track, ArrayList<LightLeaderCard> newSlot) {
        super(username, MessageType.LEADERCARDTHROWUPDATE);
        this.success = true;
        this.message = "LeaderCard successfully threw";
        this.track = track;
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
            controller.updateFaithTrack(getUsername(), this.track);
            controller.updateLeaderSlot(getUsername(), this.newSlot);
            controller.showSuccess(message);
            //todo: è nei messaggi che viene deciso cosa può fare dopo l'utente
        }
        else{
            controller.showError(getUsername(), message);
        }
    }
}

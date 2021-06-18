package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import clientModel.strongbox.LightStrongbox;
import clientModel.warehouse.LightWarehouseDepot;
import network.messages.MessageType;

import java.util.ArrayList;

public class LeaderCardActivationResponse extends GameMessage{
    private boolean success;
    private String message;
    private ArrayList<LightLeaderCard> newSlot;
    private LightWarehouseDepot warehouse;
    private LightStrongbox strongbox;

    public LeaderCardActivationResponse(String username,
                                        ArrayList<LightLeaderCard> newSlot,
                                        LightWarehouseDepot warehouse,
                                        LightStrongbox strongbox) {
        super(username, MessageType.LEADERCARDUPDATE);
        this.success = true;
        this.message = " has activated a leader card";
        this.newSlot = newSlot;
        this.warehouse = warehouse;
        this.strongbox = strongbox;
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
            controller.updateStrongbox(getUsername(), this.strongbox);
            controller.updateWarehouse(getUsername(), this.warehouse);
            controller.showOthersActions(getUsername(), this.message);
        }
        else{
            controller.showError(getUsername(), message);
        }
    }


}

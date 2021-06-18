package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightDevelopmentCard;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDevelopmentBoard;
import clientModel.warehouse.LightWarehouseDepot;
import network.messages.MessageType;

import java.util.ArrayList;

public class BuyDevCardResponse extends GameMessage{
    private ArrayList<LightDevelopmentCard> newCardSlot;
    private LightDevelopmentBoard newDevBoard;
    private LightWarehouseDepot newWarehouse;
    private LightStrongbox newStrongbox;
    private boolean success;
    private String message;

    public BuyDevCardResponse(String username, ArrayList<LightDevelopmentCard> newCardSlot, LightDevelopmentBoard newDevBoard,
                                LightWarehouseDepot newWarehouse, LightStrongbox newStrongbox) {
        super(username, MessageType.BUYDEVCARDSUPDATE);
        this.newCardSlot = newCardSlot;
        this.newDevBoard = newDevBoard;
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
        this.message = " has bought a development card";
        this.success= true;
    }

    public BuyDevCardResponse(String username, String message){
        super(username, MessageType.MARKET);
        this.message = message;
        this.success = false;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateCardSlots(getUsername(), newCardSlot);
            controller.updateDevBoard(newDevBoard);
            controller.updateStrongbox(getUsername(), newStrongbox);
            controller.updateWarehouse(getUsername(), newWarehouse);
            controller.showOthersActions(getUsername(), this.message);
        }else{
            controller.showError(getUsername(), message);
        }
    }


}

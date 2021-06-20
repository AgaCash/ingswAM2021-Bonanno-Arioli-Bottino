package network.messages.gameMessages;

import clientController.LightController;
import clientModel.strongbox.LightStrongbox;
import clientModel.warehouse.LightWarehouseDepot;
import network.messages.MessageType;

public class DefaultProductionResponse extends GameMessage {
    private LightWarehouseDepot newWarehouse;
    private LightStrongbox newStrongbox;
    private boolean success;
    private String message;

    public DefaultProductionResponse(String username, LightWarehouseDepot newWarehouse, LightStrongbox newStrongbox) {
        super(username, MessageType.DEFPRODUCTIONUPDATE);
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
        this.success = true;
    }

    public DefaultProductionResponse(String username, String message){
        super(username, MessageType.DEFPRODUCTIONUPDATE);
        this.success = false;
        this.message = message;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateWarehouse(getUsername(), newWarehouse);
            controller.updateStrongbox(getUsername(), newStrongbox);
            controller.showOthersActions(getUsername(), " has activated a production from the player board");
            if(controller.getUsername().equals(getUsername()))
                controller.showSuccess("successful production!");
        }
        else{
            controller.showError(getUsername(), message);
        }
    }
}

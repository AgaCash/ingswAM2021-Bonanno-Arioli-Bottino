package network.messages.gameMessages;

import clientController.LightController;
import clientModel.strongbox.LightStrongbox;
import clientModel.warehouse.LightWarehouseDepot;
import network.messages.MessageType;

public class DevCardProductionResponse extends GameMessage{
    private LightWarehouseDepot newWarehouse;
    private LightStrongbox newStrongbox;
    private int position;
    private String message;
    private boolean success;

    public DevCardProductionResponse(String username, LightWarehouseDepot newWarehouse, LightStrongbox newStrongbox, int position) {
        super(username, MessageType.DEVCARDPRODUCTIONUPDATE);
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
        this.position = position;
        this.success = true;
    }

    public DevCardProductionResponse(String username, String message){
        super(username, MessageType.DEVCARDPRODUCTIONUPDATE);
        this.message = message;
        this.success = false;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateWarehouse(getUsername(), newWarehouse);
            controller.updateStrongbox(getUsername(), newStrongbox);
            controller.getPlayerBoard().getFaithTrack().setCurrentPos(position);
            showUpdates(controller);
            controller.showSuccess(getUsername(), "successful production!");
        }
        else{
            controller.showError(getUsername(), message);
        }

    }

    private void showUpdates(LightController controller){
        controller.showOthersActions(getUsername(), " has produced from a card");
        controller.showOthersActions(getUsername(), " strongbox after production: "+newStrongbox.toString());
    }

}

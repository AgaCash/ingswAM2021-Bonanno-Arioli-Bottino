package network.messages.gameMessages;

import clientController.LightController;
import clientModel.strongbox.LightStrongbox;
import clientModel.warehouse.LightWarehouseDepot;
import network.messages.MessageType;

public class DevCardProductionResponse extends GameMessage{
    private LightWarehouseDepot newWarehouse;
    private LightStrongbox newStrongbox;
    private String message;
    private boolean success;

    public DevCardProductionResponse(String username, LightWarehouseDepot newWarehouse, LightStrongbox newStrongbox) {
        super(username, MessageType.PRODUCTIONUPDATE);
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
        this.success = true;
    }

    public DevCardProductionResponse(String username, String message){
        super(username, MessageType.PRODUCTION);
        this.message = message;
        this.success = false;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateWarehouse(getUsername(), newWarehouse);
            controller.updateStrongbox(getUsername(), newStrongbox);
        }
        else{
            controller.showError(getUsername(), message);
        }

    }

}

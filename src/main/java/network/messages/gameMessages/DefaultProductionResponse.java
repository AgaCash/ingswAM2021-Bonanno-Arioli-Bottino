package network.messages.gameMessages;

import clientController.LightController;
import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;
import network.messages.MessageType;

public class DefaultProductionResponse extends GameMessage {
    private WarehouseDepot newWarehouse;
    private Strongbox newStrongbox;
    private boolean success;
    private String message;

    public DefaultProductionResponse(String username, WarehouseDepot newWarehouse, Strongbox newStrongbox) {
        super(username, MessageType.PRODUCTIONUPDATE);
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
        this.success = true;
    }

    public DefaultProductionResponse(String username, String message){
        super(username, MessageType.PRODUCTIONUPDATE);
        this.success = false;
        this.message = message;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
           // controller.updateWarehouse(getUsername(), newWarehouse);
            controller.updateStrongbox(getUsername(), newStrongbox);
        }
        else{
            controller.showError(getUsername(), message);
        }
    }
}

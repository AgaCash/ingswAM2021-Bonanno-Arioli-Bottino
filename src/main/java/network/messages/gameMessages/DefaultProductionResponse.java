package network.messages.gameMessages;

import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;
import network.messages.MessageType;

public class DefaultProductionResponse extends GameMessage {
    private WarehouseDepot newWarehouse;
    private Strongbox newStrongbox;

    public DefaultProductionResponse(String username, WarehouseDepot newWarehouse, Strongbox newStrongbox) {
        super(username, MessageType.PRODUCTIONUPDATE);
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
    }

    public void executeCommand(){
        //controller.updateWarehouse(username, newWarehouse)
        //controller.updateStrongbox(username, newStrongbox);

    }
}

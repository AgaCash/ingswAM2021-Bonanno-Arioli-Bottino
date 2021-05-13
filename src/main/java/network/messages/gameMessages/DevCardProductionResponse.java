package network.messages.gameMessages;

import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;
import network.messages.MessageType;

public class DevCardProductionResponse extends GameMessage{
    private WarehouseDepot newWarehouse;
    private Strongbox newStrongbox;

    public DevCardProductionResponse(String username, WarehouseDepot newWarehouse, Strongbox newStrongbox) {
        super(username, MessageType.PRODUCTIONUPDATE);
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
    }

    public void executeCommand(){
        //controller.updateWarehouse(username, newWarehouse)
        //controller.updateStrongbox(username, newStrongbox);

    }
}

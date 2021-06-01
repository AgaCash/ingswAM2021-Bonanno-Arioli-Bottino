package network.messages.gameMessages;

import clientController.LightController;
import clientModel.resources.LightResource;
import clientModel.table.LightMarketBoard;
import clientModel.warehouse.LightWarehouseDepot;
import network.messages.MessageType;

import java.util.ArrayList;

public class BuyResourcesResponse extends GameMessage{
    private LightWarehouseDepot newWarehouse;
    private LightMarketBoard newMarketBoard;
    private ArrayList<LightResource> threwResource;
    private boolean success;
    private String message;

    public BuyResourcesResponse(String username, LightWarehouseDepot newWarehouse, LightMarketBoard newMarketBoard,
                                ArrayList<LightResource> threwResource){
        super(username, MessageType.MARKET);
        this.newWarehouse = newWarehouse;
        this.newMarketBoard = newMarketBoard;
        if(threwResource==null)
            threwResource=new ArrayList<>();
        else
            this.threwResource = threwResource;
        this.success = true;
    }

    public BuyResourcesResponse(String username, String message){
        super(username, MessageType.MARKET);
        this.message = message;
        this.success = false;

    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateWarehouse(getUsername(), newWarehouse);
            controller.updateMarketBoard(newMarketBoard);
            controller.showThrewResources(getUsername(), threwResource);
        }
        else
            controller.showError(getUsername(), message);
    }



}

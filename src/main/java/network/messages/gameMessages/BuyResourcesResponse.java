package network.messages.gameMessages;

import clientController.LightController;
import model.resources.Resource;
import model.table.FaithTrack;
import model.table.MarketBoard;
import model.warehouse.WarehouseDepot;
import network.messages.MessageType;

import java.util.ArrayList;

public class BuyResourcesResponse extends GameMessage{
    private WarehouseDepot newWarehouse;
    private MarketBoard newMarketBoard;
    private ArrayList<Resource> threwResource;
    private boolean success;
    private String message;

    public BuyResourcesResponse(String username, WarehouseDepot newWarehouse, MarketBoard newMarketBoard,
                                ArrayList<Resource> threwResource, ArrayList<FaithTrack> faithTracks){
        super(username, MessageType.MARKET);
        this.newWarehouse = newWarehouse;
        this.newMarketBoard = newMarketBoard;
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

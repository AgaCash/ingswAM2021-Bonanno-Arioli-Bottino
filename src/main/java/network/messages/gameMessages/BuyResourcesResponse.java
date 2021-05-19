package network.messages.gameMessages;

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

    public BuyResourcesResponse(String username, WarehouseDepot newWarehouse, MarketBoard newMarketBoard,
                                ArrayList<Resource> threwResource, ArrayList<FaithTrack> faithTracks){
        super(username, MessageType.MARKET);
        this.newWarehouse = newWarehouse;
        this.newMarketBoard = newMarketBoard;
        this.threwResource = threwResource;
    }

    public void executeCommand(){
        //controller.updateWarehouse(username, newWarehouse)
        //controller.updateMarket(newMarket);
        //for(Resource res: threwResources) {
        //            ThrewResourcesNotify threwNotify = new ThrewResourcesNotify(getUsername(),
        //                    "Resource " + res + " couldn't be added: warehouse is full!");
        //todo: printare la lista di risorse rifiutate

    }

}

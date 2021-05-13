package network.messages.gameMessages;

import model.table.MarketBoard;
import model.warehouse.WarehouseDepot;
import network.messages.MessageType;

public class BuyResourcesResponse extends GameMessage{
    private WarehouseDepot newWarehouse;
    private MarketBoard newMarketBoard;

    public BuyResourcesResponse(String username, WarehouseDepot newWarehouse, MarketBoard newMarketBoard){
        super(username, MessageType.MARKET);
        this.newWarehouse = newWarehouse;
        this.newMarketBoard = newMarketBoard;
    }

    public void executeCommand(){
        //controller.updateWarehouse(username, newWarehouse)
        //controller.updateMarket(newMarket);

    }

}

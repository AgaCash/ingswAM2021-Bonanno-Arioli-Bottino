package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.table.LightMarketBoard;
import clientModel.warehouse.LightWarehouseDepot;
import network.messages.MessageType;

import java.util.ArrayList;

public class BuyResourcesResponse extends GameMessage{
    private LightWarehouseDepot newWarehouse;
    private LightMarketBoard newMarketBoard;
    private ArrayList<LightResource> threwResource;
    private ArrayList<LightPlayer> players;
    private int position;
    private boolean success;
    private String message;

    public BuyResourcesResponse(String username, LightWarehouseDepot newWarehouse, LightMarketBoard newMarketBoard,
                                int position,
                                ArrayList<LightResource> threwResource,
                                ArrayList<LightPlayer> players){
        super(username, MessageType.MARKETUPDATE);
        this.newWarehouse = newWarehouse;
        this.newMarketBoard = newMarketBoard;
        this.position = position;
        if(threwResource==null)
            this.threwResource=new ArrayList<>();
        else
            this.threwResource = threwResource;
        this.success = true;
        this.message = " has bought resources from the market";
        this.players = players;
    }

    public BuyResourcesResponse(String username, String message){
        super(username, MessageType.MARKETUPDATE);
        this.message = message;
        this.success = false;

    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateMarketBoard(newMarketBoard);
            for(LightPlayer p : players)
                if(controller.getUsername().equals(p.getNickname()))
                    controller.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
            controller.updateWarehouse(getUsername(), newWarehouse);
            //controller.showSuccess(newMarketBoard.toString());
            //controller.getPlayerBoard().getFaithTrack().setCurrentPos(position);
            controller.showThrewResources(getUsername(), threwResource);
            controller.showOthersActions(getUsername(), this.message);
        }
        else
            controller.showError(getUsername(), message);
    }



}

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
    private boolean isSinglePlayer;

    public BuyResourcesResponse(String username, LightWarehouseDepot newWarehouse, LightMarketBoard newMarketBoard,
                                int position,
                                ArrayList<LightResource> threwResource){
        super(username, MessageType.MARKETUPDATE);
        this.newWarehouse = newWarehouse;
        this.newMarketBoard = newMarketBoard;
        this.position = position;
        if(threwResource==null)
            this.threwResource=new ArrayList<>();
        else
            this.threwResource = threwResource;
        this.success = true;
        this.isSinglePlayer = true;
    }

    public BuyResourcesResponse(String username, LightWarehouseDepot newWarehouse, LightMarketBoard newMarketBoard,
                                ArrayList<LightResource> threwResource,
                                ArrayList<LightPlayer> players){
        //todo add faithtrack
        super(username, MessageType.MARKETUPDATE);
        this.newWarehouse = newWarehouse;
        this.newMarketBoard = newMarketBoard;
        if(threwResource==null)
            this.threwResource=new ArrayList<>();
        else
            this.threwResource = threwResource;
        this.success = true;
        this.players = players;
        this.isSinglePlayer = false;
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
            controller.updateWarehouse(getUsername(), newWarehouse);
            if (!this.isSinglePlayer) {
                controller.showOthersActions(getUsername(), " has bought resources from the market");
                for (LightPlayer p : players)
                    if (controller.getUsername().equals(p.getNickname())) {
                        if(!threwResource.isEmpty())
                            controller.showSuccess("+++ you earned "+ threwResource.size() +" faith points!");
                        controller.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
                    }
            }else{
                controller.getPlayerBoard().getFaithTrack().setCurrentPos(position);
            }
            controller.showThrewResources(getUsername(), threwResource);
            if(controller.getUsername().equals(getUsername()))
                controller.showSuccess("successful purchase!");
        }
        else
            controller.showError(getUsername(), message);
    }



}

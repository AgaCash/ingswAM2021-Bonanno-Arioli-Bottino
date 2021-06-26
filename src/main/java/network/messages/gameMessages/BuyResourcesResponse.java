package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.table.LightMarketBoard;
import network.messages.MessageType;

import java.util.ArrayList;
import java.util.Objects;

public class BuyResourcesResponse extends GameMessage{
    private LightMarketBoard newMarketBoard;
    private ArrayList<LightPlayer> players;
    private ArrayList<LightResource> threwResource;
    private boolean success;
    private String message;
    //private boolean isSinglePlayer;

    public BuyResourcesResponse(String username, ArrayList<LightPlayer> players,
                                ArrayList<LightResource> threwResource, LightMarketBoard market){
        super(username, MessageType.MARKETUPDATE);
        this.newMarketBoard = market;
        this.players = players;
        this.threwResource = Objects.requireNonNullElseGet(threwResource, ArrayList::new);
        this.success = true;
    }


    public BuyResourcesResponse(String username, String message){
        super(username, MessageType.MARKETUPDATE);
        this.message = message;
        this.success = false;

    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateBuyResources(getUsername(), players);
            controller.updateMarketBoard(newMarketBoard);
            controller.showThrewResources(getUsername(), threwResource);
            controller.showSuccess(getUsername(), "successful purchase!");
        }
        else
            controller.showError(getUsername(), message);
    }




}

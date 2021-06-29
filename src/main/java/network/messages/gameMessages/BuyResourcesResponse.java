package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.table.LightMarketBoard;
import network.messages.MessageType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Message class that implements the Resource purchase at MarketBoard response.
 */
public class BuyResourcesResponse extends GameMessage{
    private LightMarketBoard newMarketBoard;
    private ArrayList<LightPlayer> players;
    private ArrayList<LightResource> threwResource;
    private final boolean success;
    private String message;

    /**Constructor called if the action was successful
     * @param username the Player username who called the action
     * @param players the entire Players List converted in LightPlayer instances
     * @param threwResource the LightResource ArrayList copy of not accepted in Sender's WarehouseDepot Resources
     * @param market the new MarketBoard converted in a MarketBoard instance
     */
    public BuyResourcesResponse(String username, ArrayList<LightPlayer> players,
                                ArrayList<LightResource> threwResource, LightMarketBoard market){
        super(username, MessageType.MARKETUPDATE);
        this.newMarketBoard = market;
        this.players = players;
        this.threwResource = Objects.requireNonNullElseGet(threwResource, ArrayList::new);
        this.success = true;
    }
    /**Constructor called if the action was not successful
     * @param username the Player username who called the action
     * @param message the String containing the Model error message
     */
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

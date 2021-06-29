package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import network.messages.MessageType;

import java.util.ArrayList;

/**
 * Message that implements a Production from DevelopmentCard Response
 */
public class DevCardProductionResponse extends GameMessage{
    private ArrayList<LightPlayer> players;
    private String message;
    private boolean success;

    /**Constructor called if the action was successful
     * @param username the Player username who called the action
     * @param players the entire Players List converted in LightPlayer instances
     */
    public DevCardProductionResponse(String username, ArrayList<LightPlayer> players) {
        super(username, MessageType.DEVCARDPRODUCTIONUPDATE);
        this.players = players;
        this.success = true;
    }

    /**Constructor called if the action was not successful
     * @param username the Player username who called the action
     * @param message the String containing the Model error message
     */
    public DevCardProductionResponse(String username, String message){
        super(username, MessageType.DEVCARDPRODUCTIONUPDATE);
        this.message = message;
        this.success = false;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateProduction(getUsername(), players);
            controller.showSuccess(getUsername(), "successful production!");
        }
        else{
            controller.showError(getUsername(), message);
        }

    }

}

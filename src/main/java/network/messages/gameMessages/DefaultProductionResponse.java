package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import network.messages.MessageType;

import java.util.ArrayList;

/**
 * Message that implements a Default Production Response
 */
public class DefaultProductionResponse extends GameMessage {
    private ArrayList<LightPlayer> players = new ArrayList<>();
    private boolean success;
    private String message;

    /**Constructor called if the action was successful
     * @param username the Player username who called the action
     * @param players the entire Players List converted in LightPlayer instances
     */
    public DefaultProductionResponse(String username, ArrayList<LightPlayer> players) {
        super(username, MessageType.DEFPRODUCTIONUPDATE);
        this.players = players;
        this.success = true;
    }

    /**Constructor called if the action was not successful
     * @param username the Player username who called the action
     * @param message the String containing the Model error message
     */
    public DefaultProductionResponse(String username, String message){
        super(username, MessageType.DEFPRODUCTIONUPDATE);
        this.success = false;
        this.message = message;
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

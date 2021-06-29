package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import network.messages.MessageType;

import java.util.ArrayList;
/**
 * Message that implements a LeaderCard throwing Response
 */
public class LeaderCardThrowResponse extends GameMessage{
    private ArrayList<LightPlayer> players;
    private boolean success;
    private String message;

    /**Constructor called if the action was successful
     * @param username the Player username who called the action
     * @param players the entire Players List converted in LightPlayer instances
     */
    public LeaderCardThrowResponse(String username, ArrayList<LightPlayer> players) {
        super(username, MessageType.LEADERCARDTHROWUPDATE);
        this.success = true;
        this.players = players;
    }

    /**Constructor called if the action was not successful
     * @param username the Player username who called the action
     * @param message the String containing the Model error message
     */
    public LeaderCardThrowResponse(String username, String message){
        super(username, MessageType.LEADERCARDUPDATE);
        this.message = message;
        this.success = false;
    }

    @Override
    public void executeCommand(LightController controller){
        if(this.success){
            controller.updateLeaderCardThrow(getUsername(), players);
            controller.showSuccess(getUsername(), "successfully throwing!");
        }
        else{
            controller.showError(getUsername(), message);
        }
    }
}

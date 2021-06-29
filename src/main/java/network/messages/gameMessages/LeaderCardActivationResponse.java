package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import network.messages.MessageType;

import java.util.ArrayList;

/**
 * Message that implements a LeaderCard activation Response
 */
public class LeaderCardActivationResponse extends GameMessage{
    private ArrayList<LightPlayer> players;
    private boolean success;
    private String message;

    /**Constructor called if the action was successful
     * @param username the Player username who called the action
     * @param players the entire Players List converted in LightPlayer instances
     */
    public LeaderCardActivationResponse(String username,
                                        ArrayList<LightPlayer> players) {
        super(username, MessageType.LEADERCARDUPDATE);
        this.success = true;
        this.players = players;
    }

    /**Constructor called if the action was not successful
     * @param username the Player username who called the action
     * @param message the String containing the Model error message
     */
    public LeaderCardActivationResponse(String username, String message){
        super(username, MessageType.LEADERCARDUPDATE);
        this.message = message;
        this.success = false;
        System.out.println(message);
    }

    @Override
    public void executeCommand(LightController controller){
        if(this.success){
            controller.updateLeaderCardActivation(getUsername(), players);
            controller.showSuccess(getUsername(), "successful activation!");
        }
        else{
            controller.showError(getUsername(), message);
        }
    }
}

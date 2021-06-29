package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.table.LightDevelopmentBoard;
import network.messages.MessageType;

import java.util.ArrayList;

/**
 * Message class that implements a DevelopmentCard purchase response.
 */
public class BuyDevCardResponse extends GameMessage{
    private ArrayList<LightPlayer> players = new ArrayList<>();
    private LightDevelopmentBoard board;
    private final boolean success;
    private String message;

    /**Constructor called if the action was successful
     * @param username the Player username who called the action
     * @param players the entire Players List converted in LightPlayer instances
     * @param board the new DevelopmentBoard converted in a LightDevelopmentBoard instance
     */
    public BuyDevCardResponse(String username, ArrayList<LightPlayer> players, LightDevelopmentBoard board) {
        super(username, MessageType.BUYDEVCARDSUPDATE);
        this.players = players;
        this.board = board;
        this.success= true;
    }

    /**Constructor called if the action was not successful
     * @param username the Player username who called the action
     * @param message the String containing the Model error message
     */
    public BuyDevCardResponse(String username, String message){
        super(username, MessageType.MARKET);
        this.message = message;
        this.success = false;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateBuyDevCard(getUsername(), players);
            controller.updateDevBoard(board);
            controller.showSuccess(getUsername(), "successful purchase!");
        }else{
            controller.showError(getUsername(), message);
        }
    }


}

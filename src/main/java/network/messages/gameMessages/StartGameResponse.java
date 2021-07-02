package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import network.messages.MessageType;

import java.util.ArrayList;

/**
 * Message that implements sends to User the initial items to choose
 */
public class StartGameResponse extends GameMessage {
    private ArrayList<LightLeaderCard> quartet = new ArrayList<>();
    private int numResources;
    private boolean faithPoint;
    private boolean success;
    private String message;
    private int numPlayers;
    private ArrayList<String> usernamesList;

    /**Constructor if Request was successful
     * @param username the request Sender's username
     * @param quartet the 4-length LightLeaderCard ArrayList
     * @param numResources the number of resources user has to choose
     * @param faithPoint true if Player gained a FaithPoint
     * @param numPlayers the number of Players in Lobby
     * @param usernamesList the Player's username ArrayList
     */
    public StartGameResponse(String username, ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoint,
                             int numPlayers, ArrayList<String> usernamesList){
        super(username, MessageType.STARTGAMERESPONSE);
        this.quartet = quartet;
        this.numResources = numResources;
        this.faithPoint = faithPoint;
        this.success = true;
        this.numPlayers = numPlayers;
        this.usernamesList = usernamesList;
    }

    /**Constructor if Request was failed
     * @param username the Request Sender's username
     * @param message a String containing the message
     */
    public StartGameResponse(String username, String message){
        super(username, MessageType.STARTGAMERESPONSE);
        this.message = message;
        this.success = false;
    }

    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.showSuccess("starting game ...");
            controller.chooseStartItems(this.quartet, this.numResources, this.faithPoint);
            controller.setNumOfPlayerInLobby(numPlayers);
            controller.setUsernamesList(usernamesList);
        }
        else{
            controller.showError(getUsername(), message);
        }
    }
}

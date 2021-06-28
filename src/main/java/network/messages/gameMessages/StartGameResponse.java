package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import network.messages.MessageType;

import java.util.ArrayList;

public class StartGameResponse extends GameMessage {
    private ArrayList<LightLeaderCard> quartet = new ArrayList<>();
    private int numResources;
    private boolean faithPoint;
    private boolean success;
    private String message;
    private int numPlayers;
    private ArrayList<String> usernamesList;

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

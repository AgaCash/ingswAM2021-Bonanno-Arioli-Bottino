package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import com.google.gson.Gson;
import model.cards.LeaderCard;
import network.messages.MessageType;

import java.util.ArrayList;

public class StartGameResponse extends GameMessage {
    private ArrayList<LightLeaderCard> quartet = new ArrayList<>();
    private int numResources;
    private boolean faithPoint;
    private boolean success;
    private String message;

    public StartGameResponse(String username, ArrayList<LeaderCard> quartet, int numResources, boolean faithPoint){
        super(username, MessageType.STARTGAMERESPONSE);
        for(LeaderCard card : quartet)
            this.quartet.add(card.convert());
        quartet.forEach(element -> System.out.println(element));
        this.numResources = numResources;
        this.faithPoint = faithPoint;
        this.success = true;

    }

    public StartGameResponse(String username, String message){
        super(username, MessageType.STARTGAMERESPONSE);
        this.message = message;
        this.success = false;
    }

    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.showSuccess(getUsername(), "starting single player game ...");
            System.out.println("sono la response");
            this.quartet.forEach(element -> System.out.println(element));
            System.out.println(" ci sono ");
            controller.chooseStartItems(this.quartet, this.numResources, this.faithPoint);
        }
        else{
            controller.showError(getUsername(), message);
        }
    }
}

package network.messages.gameMessages;

import model.cards.LeaderCard;
import network.messages.MessageType;

import java.util.ArrayList;

public class StartGameResponse extends GameMessage {
    private ArrayList<LeaderCard> quartet;
    private int numResources;
    private boolean faithPoint;

    public StartGameResponse(String username, ArrayList<LeaderCard> quartet, int numResources, boolean faithPoint){
        super(username, MessageType.SETUP);
        this.quartet = quartet;
        this.numResources = numResources;
        this.faithPoint = faithPoint;
    }

    public void executeCommand(){
        //minimodel
        //send new SetupRequest()
    }
}

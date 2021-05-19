package network.messages.gameMessages;

import controller.Controller;
import exceptions.FullWarehouseException;
import exceptions.NoSuchUsernameException;
import model.cards.LeaderCard;
import model.resources.Resource;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;

public class SetupRequest extends GameMessage{
    private ArrayList<LeaderCard> couple;
    private ArrayList<Resource> chosenResources;
    private boolean faithPoint;

    public SetupRequest(String username, ArrayList<LeaderCard> couple, ArrayList<Resource> chosenResources, boolean faithPoint){
        super(username, MessageType.SETUPRESPONSE);
        this.couple = couple;
        this.chosenResources = chosenResources;
        this.faithPoint = faithPoint;
    }
    @Override
    public void executeCommand(Controller controller, VirtualClient view){
        try {
            controller.setLeaderCards(getUsername(), this.couple);
        } catch(Exception e){
            controller.handleError(e.getMessage());
        }
        try{
            controller.setChosenStartup(getUsername(), this.chosenResources, this.faithPoint);
        } catch(NoSuchUsernameException | FullWarehouseException e){
            controller.handleError(e.getMessage());
        }
        try{
            if(this.faithPoint)
                controller.getPlayer(getUsername()).getPlayerBoard().getFaithTrack().faithAdvance(
                    controller.getPlayer(getUsername()).getPlayerBoard().getFaithBox(),
                    controller.getPlayer(getUsername()).getPlayerBoard().getFaithTrack()
                );
            controller.notifyReadiness();
        } catch(NoSuchUsernameException e){
            controller.handleError(e.getMessage());
        }
    }

}

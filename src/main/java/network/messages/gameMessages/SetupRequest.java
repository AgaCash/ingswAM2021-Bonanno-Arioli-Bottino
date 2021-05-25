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
        super(username, MessageType.SETUP);
        this.couple = couple;
        this.chosenResources = chosenResources;
        this.faithPoint = faithPoint;
    }
    @Override
    public void executeCommand(Controller controller, VirtualClient view){
        System.out.println("MO SO CAZZI RIGA 26");

        try {
            System.out.println("mo so cazzi riga 28");
            controller.setLeaderCards(getUsername(), this.couple);

        } catch(NoSuchUsernameException e){
            System.out.println(" mo so riga 32");
            controller.handleError(e.getMessage());

        }
        try{
            System.out.println("mo so cazzi riga 34");
            controller.setChosenStartup(getUsername(), this.chosenResources, this.faithPoint);

        } catch(NoSuchUsernameException | FullWarehouseException e){
            System.out.println(" mo so riga 39");
            controller.handleError(e.getMessage());
        }
        try{
            if(this.faithPoint)
                controller.getPlayer(getUsername()).getPlayerBoard().getFaithTrack().faithAdvance(
                    controller.getPlayer(getUsername()).getPlayerBoard().getFaithBox(),
                    controller.getPlayer(getUsername()).getPlayerBoard().getFaithTrack()
                );
            System.out.println("mo so cazzi riga 44");
            controller.notifyReadiness();
        } catch(NoSuchUsernameException e){
            controller.handleError(e.getMessage());
            System.out.println(" mo so riga 51");
        }
    }

}

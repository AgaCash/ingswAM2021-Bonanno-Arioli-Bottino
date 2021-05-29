package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import exceptions.FullWarehouseException;
import exceptions.NoSuchUsernameException;
import model.cards.LeaderCard;
import model.resources.Resource;
import utilities.LeaderCardDeserializer;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;

public class SetupRequest extends GameMessage{
    private ArrayList<LightLeaderCard> couple;
    private ArrayList<LightResource> chosenResources;
    private boolean faithPoint;

    public SetupRequest(String username, ArrayList<LightLeaderCard> couple, ArrayList<LightResource> chosenResources, boolean faithPoint){
        super(username, MessageType.SETUP);
        this.couple = couple;
        this.chosenResources = chosenResources;
        this.faithPoint = faithPoint;
    }
    @Override
    public void executeCommand(Controller controller, VirtualClient view){
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();

        ArrayList<LeaderCard> couple = new ArrayList<>();
        for(LightLeaderCard card : this.couple)
            couple.add(gson.fromJson(gson.toJson(card), LeaderCard.class));
        try {
            controller.setLeaderCards(getUsername(), couple);

        } catch(NoSuchUsernameException e){
            controller.handleError(e.getMessage());

        }

        ArrayList<Resource> chosenResources = new ArrayList<>();
        for(LightResource res : this.chosenResources)
            chosenResources.add(Resource.valueOf(res.toString()));
        try{
            controller.setChosenStartup(getUsername(), chosenResources, this.faithPoint);

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

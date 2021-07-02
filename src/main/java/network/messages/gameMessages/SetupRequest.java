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

/**
 * Message that sends to server the chosen starting items
 */
public class SetupRequest extends GameMessage{
    private ArrayList<LightLeaderCard> couple;
    private ArrayList<LightResource> chosenResources;
    private boolean faithPoint;

    /**Constructor
     * @param username the Sender's username
     * @param couple the 2 -length LeaderCard ArrayList that will be added to PlayerBoard
     * @param chosenResources the Resource ArrayList will be added to WarehouseDepot
     * @param faithPoint true if Player's gained a FaithPoint
     */
    public SetupRequest(String username, ArrayList<LightLeaderCard> couple, ArrayList<LightResource> chosenResources, boolean faithPoint){
        super(username, MessageType.SETUP);
        this.couple = couple;
        this.chosenResources = chosenResources;
        this.faithPoint = faithPoint;
    }
    @Override
    public void executeCommand(Controller controller, VirtualClient view){
        System.out.println(this.couple);
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
            controller.setChosenStartup(getUsername(), chosenResources);

        } catch(NoSuchUsernameException | FullWarehouseException e){
            controller.handleError(e.getMessage());
        }
        try{
            if(this.faithPoint)
                controller.getPlayer(getUsername()).getPlayerBoard().getFaithTrack().faithAdvance();
            controller.notifyReadiness();
        } catch(NoSuchUsernameException e){
            controller.handleError(e.getMessage());
        }
    }

}

package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import exceptions.InsufficientRequirementsException;
import exceptions.InsufficientResourcesException;
import model.cards.LeaderCard;
import utilities.LeaderCardDeserializer;
import network.messages.MessageType;
import view.VirtualClient;

public class LeaderCardActivationRequest extends GameMessage{
    private LightLeaderCard card;

    public LeaderCardActivationRequest(String username, LightLeaderCard card){
        super(username, MessageType.LEADERCARD);
        this.card = card;
        System.out.println(this.card);
    }


    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        System.out.println(this.card);
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        LeaderCard card = gson.fromJson(gson.toJson(this.card), LeaderCard.class);
        System.out.println(card);
        try {
            controller.activateLeaderCard(card);
            update(controller);
        } catch (InsufficientRequirementsException | InsufficientResourcesException e){
            LeaderCardActivationResponse notify = new LeaderCardActivationResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateLeaderCardActivation(notify);
        }

    }

    public void update(Controller controller){
        LeaderCardActivationResponse response = new LeaderCardActivationResponse(getUsername());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateLeaderCardActivation(response);});
    };
}

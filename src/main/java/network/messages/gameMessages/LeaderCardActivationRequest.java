package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import exceptions.InsufficientRequirementsException;
import exceptions.InsufficientResourcesException;
import model.cards.LeaderCard;
import network.messages.MessageType;
import utilities.LeaderCardDeserializer;
import view.VirtualClient;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class LeaderCardActivationRequest extends GameMessage{
    private LightLeaderCard card;

    public LeaderCardActivationRequest(String username, LightLeaderCard card){
        super(username, MessageType.LEADERCARD);
        this.card = card;
    }


    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        try {
            controller.activateLeaderCard(card);
            update(controller);
        } catch (InsufficientRequirementsException | InsufficientResourcesException | InputMismatchException e){
            LeaderCardActivationResponse notify = new LeaderCardActivationResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateLeaderCardActivation(notify);
        }

    }

    public void update(Controller controller){
        ArrayList<LightLeaderCard> newLeaderSlot = new ArrayList<>();
        controller.getCurrentPlayer().getPlayerBoard().getLeaders().forEach(e-> newLeaderSlot.add(e.convert()));
        LeaderCardActivationResponse response = new LeaderCardActivationResponse(getUsername(),
                newLeaderSlot);
        controller.getViews().forEach((element)-> { element.getVirtualView().updateLeaderCardActivation(response);});
    };
}

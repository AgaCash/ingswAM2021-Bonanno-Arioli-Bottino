package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientRequirementsException;
import exceptions.InsufficientResourcesException;
import model.cards.LeaderCard;
import network.messages.MessageType;
import view.VirtualClient;

public class LeaderCardActivationRequest extends GameMessage{
    private LeaderCard card;

    public LeaderCardActivationRequest(String username, LeaderCard card){
        super(username, MessageType.LEADERCARD);
        this.card = card;
    }


    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        Gson gson = new Gson();
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

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

    public LeaderCardActivationRequest(String username){
        super(username, MessageType.LEADERCARD);
    }


    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        Gson gson = new Gson();
        try {
            controller.activateLeaderCard(card);
            update(controller);
        } catch (InsufficientRequirementsException | InsufficientResourcesException e){
            FailedActionNotify notify = new FailedActionNotify(this.getUsername(), e.getMessage());
            client.getVirtualView().update(notify);
        }

    }

    public void update(Controller controller){
        LeaderCardActivationResponse response = new LeaderCardActivationResponse(getUsername());
        controller.getViews().forEach((element)-> { element.getVirtualView().update(response);});
    };
}

package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.InsufficientRequirementsException;
import exceptions.InsufficientResourcesException;
import model.cards.LeaderCard;
import network.messages.MessageType;
import network.messages.notifies.FailedActionNotify;
import view.VirtualView;
import java.util.ArrayList;

public class LeaderCardActivationRequest extends GameMessage{
    private LeaderCard card;

    public LeaderCardActivationRequest(String username){
        super(username, MessageType.LEADERCARD);
    }


    @Override
    public void executeCommand(Controller controller, ArrayList<VirtualView> views) {
        Gson gson = new Gson();
        try {
            controller.activateLeaderCard(card);
           //out.println(gson.toJson(new LeaderCardActivationResponse(this.getUsername()), LeaderCardActivationResponse.class));
            update();
        } catch (InsufficientRequirementsException | InsufficientResourcesException e){
           //out.println(gson.toJson(new FailedActionNotify(this.getUsername(), e.getMessage()), FailedActionNotify.class));

        }

    }

    public void update(){

    };
}

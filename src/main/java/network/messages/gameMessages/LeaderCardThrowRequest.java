package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Controller;
import model.cards.LeaderCard;
import network.messages.MessageType;
import utilities.LeaderCardDeserializer;
import view.VirtualClient;

import java.util.ArrayList;

public class LeaderCardThrowRequest extends GameMessage{
    private LightLeaderCard card;

    public LeaderCardThrowRequest(String username, LightLeaderCard card){
        super(username, MessageType.LEADERCARDTHROW);
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
            controller.throwLeaderCard(card);
            update(controller);
        } catch (Exception e){
            LeaderCardThrowResponse notify = new LeaderCardThrowResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateLeaderCardThrow(notify);
        }

    }

    public void update(Controller controller){
        ArrayList<LightLeaderCard> newLeaderSlot = new ArrayList<>();
        controller.getCurrentPlayer().getPlayerBoard().getLeaders().forEach(e-> newLeaderSlot.add(e.convert()));

        LeaderCardThrowResponse response = new LeaderCardThrowResponse(getUsername(),
                controller.getCurrentPlayer().getPlayerBoard().getFaithTrack().convert(),
                newLeaderSlot);
        controller.getViews().forEach((element)-> { element.getVirtualView().updateLeaderCardThrow(response);});
    };
}

package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import controller.Controller;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class LeaderCardThrowRequest extends GameMessage{
    private int card;

    public LeaderCardThrowRequest(String username, int card){
        super(username, MessageType.LEADERCARDTHROW);
        this.card = card;
        System.out.println(this.card);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        try {
            controller.throwLeaderCard(card);
            update(controller);
        } catch (InputMismatchException e){
            LeaderCardThrowResponse notify = new LeaderCardThrowResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateLeaderCardThrow(notify);
        }

    }

    public void update(Controller controller){
        ArrayList<LightLeaderCard> newLeaderSlot = new ArrayList<>();
        controller.getCurrentPlayer().getPlayerBoard().getLeaders().forEach(e-> newLeaderSlot.add(e.convert()));

        LeaderCardThrowResponse response = new LeaderCardThrowResponse(getUsername(),
                controller.getCurrentPlayer().getPlayerBoard().getFaithTrack().getFaithBox().getPosition(),
                newLeaderSlot);
        controller.getViews().forEach((element)-> { element.getVirtualView().updateLeaderCardThrow(response);});
    };
}

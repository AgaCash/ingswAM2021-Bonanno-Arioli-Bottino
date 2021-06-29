package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import controller.Controller;
import exceptions.InvalidActionException;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Message that implements a LeaderCard throwing Request
 */
public class LeaderCardThrowRequest extends GameMessage{
    private LightLeaderCard card;

    /**Request constructor
     * @param username Sender's username
     * @param card the LightLeaderCard copy of the LeaderCard in model will be thrown
     */
    public LeaderCardThrowRequest(String username, LightLeaderCard card){
        super(username, MessageType.LEADERCARDTHROW);
        this.card = card;
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        try {
            controller.throwLeaderCard(card);
            update(controller);
        } catch (InputMismatchException | InvalidActionException e){
            LeaderCardThrowResponse notify = new LeaderCardThrowResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateLeaderCardThrow(notify);
        }

    }

    /**Creates the Response instance if action was successful (executeCommand didn't threw Exceptions)
     * @param controller the Controller in the Server
     */
    public void update(Controller controller){
        ArrayList<LightLeaderCard> newLeaderSlot = new ArrayList<>();
        controller.getCurrentPlayer().getPlayerBoard().getLeaders().forEach(e-> newLeaderSlot.add(e.convert()));

        LeaderCardThrowResponse response = new LeaderCardThrowResponse(getUsername(),
                controller.getLightPlayers());
        controller.getViews().forEach((element)->element.getVirtualView().updateLeaderCardThrow(response));
    };
}

package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import controller.Controller;
import exceptions.InsufficientRequirementsException;
import exceptions.InsufficientResourcesException;
import exceptions.InvalidActionException;
import exceptions.UnusableCardException;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Message that implements a LeaderCard activation Request
 */
public class LeaderCardActivationRequest extends GameMessage{
    private LightLeaderCard card;

    /**Request constructor
     * @param username Sender's username
     * @param card the LightLeaderCard copy of the LeaderCard in model will be activated
     */
    public LeaderCardActivationRequest(String username, LightLeaderCard card){
        super(username, MessageType.LEADERCARD);
        this.card = card;
    }


    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        try {
            controller.activateLeaderCard(card);
            update(controller);
        } catch (InsufficientRequirementsException | InsufficientResourcesException | InputMismatchException | UnusableCardException | InvalidActionException e){
            LeaderCardActivationResponse notify = new LeaderCardActivationResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateLeaderCardActivation(notify);
        }

    }

    /**Creates the Response instance if action was successful (executeCommand didn't threw Exceptions)
     * @param controller the Controller in the Server
     */
    public void update(Controller controller){
        ArrayList<LightLeaderCard> newLeaderSlot = new ArrayList<>();
        controller.getCurrentPlayer().getPlayerBoard().getLeaders().forEach(e-> newLeaderSlot.add(e.convert()));
        LeaderCardActivationResponse response = new LeaderCardActivationResponse(getUsername(),
                controller.getLightPlayers());
        controller.getViews().forEach((element)->  element.getVirtualView().updateLeaderCardActivation(response));
    };
}

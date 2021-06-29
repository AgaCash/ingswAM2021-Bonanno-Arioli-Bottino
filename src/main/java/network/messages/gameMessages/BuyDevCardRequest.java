package network.messages.gameMessages;

import clientModel.cards.LightLeaderCard;
import controller.Controller;
import exceptions.*;
import network.messages.MessageType;
import view.VirtualClient;

/**
 *Message class that implements a DevelopmentCard purchase request.
 */
public class BuyDevCardRequest extends GameMessage{
    private int deck;
    private int slot;
    private LightLeaderCard card;


    /**Request constructor
     * @param username the Sender username
     * @param deck the Deck number in DevelopmentBoard where purchase the DevelopmentCard
     * @param slot the CardSlot number in PlayerBoard where add the DevelopmentCard after purchase
     * @param card a LightLeaderCard copy of the LeaderCard that Player want to add to the action, null if Player asked for simple action
     */
    public BuyDevCardRequest(String username, int deck, int slot, LightLeaderCard card){
        super(username, MessageType.BUYDEVCARDS);
        this.deck = deck;
        this.slot = slot;
        this.card = card;

    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        try {
            controller.buyDevCard(deck, slot, card);
            update(controller);
        } catch (FullCardSlotException | NonCorrectLevelCardException | InsufficientResourcesException | EmptyDeckException | UnusableCardException | InvalidActionException e) {
            BuyDevCardResponse notify = new BuyDevCardResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateBuyDevCard(notify);
        }
    }

    /**Creates the Response instance if action was successful (executeCommand didn't threw Exceptions)
     * @param controller the Controller in the Server
     */
    private void update(Controller controller){
        BuyDevCardResponse response = new BuyDevCardResponse(getUsername(),
                                                            controller.getLightPlayers(),
                                                            controller.getDevBoard().convert());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateBuyDevCard(response);});
    }
}

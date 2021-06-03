package network.messages.gameMessages;

import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightLeaderCard;
import controller.Controller;
import exceptions.*;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;

public class BuyDevCardRequest extends GameMessage{
    private int deck;
    private int slot;
    private LightLeaderCard card;


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
        } catch (FullCardSlotException | NonCorrectLevelCardException | InsufficientResourcesException | EmptyDeckException | UnusableCardException e) {
            BuyDevCardResponse notify = new BuyDevCardResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateBuyDevCard(notify);
        }
    }

    private void update(Controller controller){
        ArrayList<LightDevelopmentCard> slots = controller.getCurrentPlayer().getPlayerBoard().getCardSlots().convert();
        BuyDevCardResponse response = new BuyDevCardResponse(getUsername(),
                                                            slots,
                                                            controller.getDevBoard().convert(),
                                                            controller.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert(),
                                                            controller.getCurrentPlayer().getPlayerBoard().getStrongbox().convert());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateBuyDevCard(response);});
    }
}

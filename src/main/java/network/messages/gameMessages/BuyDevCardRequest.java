package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.*;
import model.cards.Discount;
import network.messages.MessageType;
import view.VirtualClient;

public class BuyDevCardRequest extends GameMessage{
    private int deck;
    private int slot;
    private Discount card;


    public BuyDevCardRequest(String username, int deck, int slot, Discount card){
        super(username, MessageType.BUYDEVCARDS);
        this.deck = deck;
        this.slot = slot;
        this.card = card;

    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        Gson gson = new Gson();
        try {
            controller.buyDevCard(deck, slot, card);
            update(controller);
        } catch (FullCardSlotException | NonCorrectLevelCardException | InsufficientResourcesException | EmptyDeckException | UnusableCardException e) {
            BuyDevCardResponse notify = new BuyDevCardResponse(this.getUsername(), e.getMessage());
            client.getVirtualView().updateBuyDevCard(notify);
        }
    }

    private void update(Controller controller){
        BuyDevCardResponse response = new BuyDevCardResponse(getUsername(),
                                                            controller.getCurrentPlayer().getPlayerBoard().getCardSlots(),
                                                            controller.getDevBoard());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateBuyDevCard(response);});
    }
}

package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.*;
import model.cards.Discount;
import model.table.Deck;
import network.messages.MessageType;
import view.VirtualClient;

public class BuyDevCardRequest extends GameMessage{
    private Deck deck;
    private int slot;
    private Discount card;

    public BuyDevCardRequest(String username){
        super(username, MessageType.BUYDEVCARDS);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        Gson gson = new Gson();
        try {
            controller.buyDevCard(deck, slot, card);
            update(controller);
        } catch (FullCardSlotException | NonCorrectLevelCardException | InsufficientResourcesException | EmptyDeckException | UnusableCardException e) {
            FailedActionNotify notify = new FailedActionNotify(this.getUsername(), e.getMessage());
            client.getVirtualView().update(notify);
        }
    }

    private void update(Controller controller){
        BuyDevCardResponse response = new BuyDevCardResponse(getUsername(),
                                                            controller.getCurrentPlayer().getPlayerBoard().getCardSlots(),
                                                            controller.getDevBoard());
        controller.getViews().forEach((element)-> { element.getVirtualView().update(response);});
    }
}

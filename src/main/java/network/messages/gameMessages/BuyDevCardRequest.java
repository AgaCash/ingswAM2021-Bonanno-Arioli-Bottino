package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.FullCardSlotException;
import exceptions.InsufficientResourcesException;
import exceptions.NonCorrectLevelCardException;
import model.cards.Discount;
import model.table.Deck;
import network.messages.MessageType;
import view.VirtualView;

import java.util.ArrayList;

public class BuyDevCardRequest extends GameMessage{
    private Deck deck;
    private int slot;
    private Discount card;

    public BuyDevCardRequest(String username){
        super(username, MessageType.BUYDEVCARDS);
    }

    @Override
    public void executeCommand(Controller controller, ArrayList<VirtualView> views) {
        Gson gson = new Gson();
        try {
            controller.buyDevCard(deck, slot, card);
           //out.println(gson.toJson(new BuyDevCardResponse(this.getUsername()), MarketResponse.class));
            update(controller, views);
        }
        catch(FullCardSlotException e){
           //out.println(gson.toJson(new FailedActionNotify(this.getUsername(), e.getMessage())));
        } catch (NonCorrectLevelCardException e) {
           //out.println(gson.toJson(new FailedActionNotify(this.getUsername(), e.getMessage())));
        } catch (InsufficientResourcesException e){
           //out.println(gson.toJson(new FailedActionNotify(this.getUsername(), e.getMessage())));
        }
    }

    private void update(Controller controller, ArrayList<VirtualView> views){
        BuyDevCardResponse response = new BuyDevCardResponse(getUsername(),
                                                            controller.getCurrentPlayer().getCardSlots(),
                                                            controller.getDevBoard());
        //views.forEach((element)-> { element.updateBuyDevCard(response);});


    }
}

package network.messages.gameMessages;

import clientController.LightController;
import clientModel.cards.LightDevelopmentCard;
import clientModel.table.LightDevelopmentBoard;
import model.cards.CardSlots;
import model.table.DevelopmentBoard;
import network.messages.MessageType;

import java.util.ArrayList;

public class BuyDevCardResponse extends GameMessage{
    private ArrayList<LightDevelopmentCard> newCardSlot;
    private LightDevelopmentBoard newDevBoard;
    private boolean success = true;
    private String message;

    public BuyDevCardResponse(String username, ArrayList<LightDevelopmentCard> newCardSlot, LightDevelopmentBoard newDevBoard) {
        super(username, MessageType.MARKETUPDATE);
        this.newCardSlot = newCardSlot;
        this.newDevBoard = newDevBoard;
        this.success= true;
    }

    public BuyDevCardResponse(String username, String message){
        super(username, MessageType.MARKET);
        this.message = message;
        this.success = false;
    }
    @Override
    public void executeCommand(LightController controller){
        if(this.success) {
            controller.updateCardSlots(getUsername(), newCardSlot);
            controller.updateDevBoard(newDevBoard);
        }else{
            controller.showError(getUsername(), message);
        }
    }


}

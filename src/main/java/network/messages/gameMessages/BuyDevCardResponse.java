package network.messages.gameMessages;

import clientController.LightController;
import model.cards.CardSlots;
import model.table.DevelopmentBoard;
import network.messages.MessageType;

public class BuyDevCardResponse extends GameMessage{
    private CardSlots newCardSlot;
    private DevelopmentBoard newDevBoard;
    private boolean success = true;
    private String message;

    public BuyDevCardResponse(String username, CardSlots newCardSlot, DevelopmentBoard newDevBoard) {
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

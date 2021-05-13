package network.messages.gameMessages;

import model.cards.CardSlots;
import model.table.DevelopmentBoard;
import network.messages.MessageType;

public class BuyDevCardResponse extends GameMessage{
    private CardSlots newCardSlot;
    private DevelopmentBoard newDevBoard;

    public BuyDevCardResponse(String username, CardSlots newCardSlot, DevelopmentBoard newDevBoard) {
        super(username, MessageType.MARKETUPDATE);
        this.newCardSlot = newCardSlot;
        this.newDevBoard = newDevBoard;

    }

    public void executeCommand(){
        //controller.updateCardSlots(username, newCardSlots)
        //controller.updateDevBoard(newDevBoard);
    }

}

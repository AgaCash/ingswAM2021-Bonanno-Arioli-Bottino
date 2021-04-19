package cards;

import java.util.ArrayList;

public class CardSlot {
    private int slotNumber;
    private ArrayList<DevelopmentCard> cards;
    private boolean isEnable;

    public CardSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.cards = new ArrayList<>();
        this.isEnable = false;
    }

    public void addCard(DevelopmentCard card){
        cards.add(card);
    }
}

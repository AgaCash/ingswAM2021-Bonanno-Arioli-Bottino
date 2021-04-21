package cards;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class CardSlots {
    private ArrayList<ArrayList<DevelopmentCard>> slots;
    private boolean isEnable;

    public CardSlots() {
        this.slots = new ArrayList<>(3);
        this.isEnable = false;
    }

    private int getIndexLastCard(int slot){
        return slots.get(slot).size()-1;
    }

    private boolean checkCardLevelIntegrity(int slot, DevelopmentCard newCard){
        DevelopmentCard oldCard = slots.get(slot).get(getIndexLastCard(slot));
        return oldCard.getLevel() == newCard.getLevel() - 1;
    }

    public void addCard (int slot, DevelopmentCard card) throws OperationNotSupportedException {
        if(slot < 0 || slot > 2)
            throw new IndexOutOfBoundsException();  //Index out of bound
        if(getIndexLastCard(slot) > 1)
            throw new OperationNotSupportedException(); //Card Slot Full
        if(!checkCardLevelIntegrity(slot, card))
            throw new OperationNotSupportedException(); //New card level not correct

        slots.get(slot).add(card);

    }
}

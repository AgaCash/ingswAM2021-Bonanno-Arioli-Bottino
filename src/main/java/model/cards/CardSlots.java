package model.cards;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class CardSlots {
    private ArrayList<Stack<DevelopmentCard>> slots;
    private boolean isEnable;

    public CardSlots() {
        this.slots = new ArrayList<>(3);
        for(int i = 0; i<3; i++)
            slots.add(new Stack<>());
        this.isEnable = false;
    }

    public boolean isPresent(DevelopmentCard toFind){
        for(Stack<DevelopmentCard> slot : slots){
            for(DevelopmentCard card : slot)
                if(card.getId() == toFind.getId())
                    return true;
        }
        return false;
    }

    public DevelopmentCard getCard(int slot){
        try {
            DevelopmentCard getCard = slots.get(slot).pop();
            slots.get(slot).push(getCard);
            return getCard;
        }catch(EmptyStackException e){
            return null;}
    }

    private boolean checkCardLevelIntegrity(int slot, DevelopmentCard newCard){
        DevelopmentCard oldCard = getCard(slot);
        return oldCard.getLevel() == newCard.getLevel() - 1;
    }

    public void addCard (int slot, DevelopmentCard card) throws OperationNotSupportedException {
        if(this.slots.get(slot).isEmpty() && card.getLevel()==1){}
        else {
            if (slot < 0 || slot > 2)
                throw new IndexOutOfBoundsException();  //Index out of bound
            if (slots.get(slot).size() > 2)
                throw new OperationNotSupportedException();//Card Slot Full
            if (!checkCardLevelIntegrity(slot, card))
                throw new OperationNotSupportedException(); //New card level not correct
        }
        slots.get(slot).push(card);
    }

}


package model.cards;

import clientModel.cards.LightDevelopmentCard;
import exceptions.FullCardSlotException;
import exceptions.NonCorrectLevelCardException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class CardSlots {
    private ArrayList<Stack<DevelopmentCard>> slots;
    private boolean isEnable;
    private int cardCount;

    public CardSlots() {
        this.slots = new ArrayList<>(3);
        for(int i = 0; i<3; i++)
            slots.add(new Stack<>());
        this.isEnable = false;
        this.cardCount = 0;
    }

    public boolean isPresent(DevelopmentCard toFind){
        for(Stack<DevelopmentCard> slot : slots){
            for(DevelopmentCard card : slot)
                if(card.getColour() == toFind.getColour() && card.getLevel() == toFind.getLevel())
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
            return null;
        }
    }

    private boolean checkCardLevelIntegrity(int slot, DevelopmentCard newCard){
        DevelopmentCard oldCard = getCard(slot);
        return oldCard.getLevel() == newCard.getLevel() - 1;
    }

    public void addCard (int slot, DevelopmentCard card) throws IndexOutOfBoundsException,
                                                                FullCardSlotException,
                                                                NonCorrectLevelCardException {
        if(this.slots.get(slot).isEmpty() && card.getLevel()==1){}
        else {
            //TODO really necessary? probably remove when client would be defined
            if (slot < 0 || slot > 2)
                throw new IndexOutOfBoundsException();
            if (slots.get(slot).size() > 2)
                throw new FullCardSlotException("Can't add the card in this slot: it's full!");//Card Slot Full
            if (!checkCardLevelIntegrity(slot, card))
                throw new NonCorrectLevelCardException("Can't add the card in this slot: incorrect level!"); //New card level not correct
        }
        slots.get(slot).push(card);
        this.cardCount++;
    }

    public ArrayList<LightDevelopmentCard> convert(){
        ArrayList<LightDevelopmentCard> slots = new ArrayList<>();
        DevelopmentCard card;
        for(int i=0; i<3; i++){
                card = getCard(i);
                if(card!=null)
                    slots.add(card.convert());
        }
        return slots;
    }

    public boolean isOver(){
        if(cardCount == 7)
            return true;
        return false;
    }


}


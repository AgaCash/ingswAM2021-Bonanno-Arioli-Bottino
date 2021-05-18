package clientModel.cards;


import java.util.ArrayList;
import java.util.EmptyStackException;


public class LightCardSlots {
    private ArrayList<LightDevelopmentCard> slots;
    private boolean isEnable;
/*
    public void setNewCard (int slot, LightDevelopmentCard card){
        slots.set(slot, card);
    }


    private boolean checkCardLevelIntegrity(int slot, DevelopmentCard newCard){
        DevelopmentCard oldCard = getCard(slot);
        return oldCard.getLevel() == newCard.getLevel() - 1;
    }

     */

    public LightDevelopmentCard getCard(int slot){
        try {
            LightDevelopmentCard getCard = slots.get(slot);
            return getCard;
        }catch(EmptyStackException e){
            return null;}
    }

   /* public boolean checkRightSlot(int slot, DevelopmentCard card){
        if(card.getLevel()==1 && slots.get(slot)==null)
            return true;
        else if(slots.get(slot).getLevel() == card.getLevel()-1)
            return true;
        else
            return true;
    }

    public DevelopmentCard getCard (int slot){
        return slots.get(slot);
    }
*/
}


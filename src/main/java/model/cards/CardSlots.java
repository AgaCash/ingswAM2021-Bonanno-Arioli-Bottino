package model.cards;

import clientModel.cards.LightCardSlots;
import clientModel.cards.LightDevelopmentCard;
import exceptions.FullCardSlotException;
import exceptions.NonCorrectLevelCardException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**The container of Player's purchased DevelopmentCards. There are three slots and for each can be used only the DevelopmentCard on the top.
 * Cards can be added only following the level rules: over a DevelopmentCard in a slot can be put only one with the next level
 * For example: over a level 1 DevelopmentCard, can be added only a level 2 DevelopmentCard, etc...
 * Player can't add a non-level 1 DevelopmentCard in an empty Slot, but can start adding DevelopmentCards in the preferred Slots
 * When 7 DevelopmentCards are added, the Game is over (Player wins)
 */
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

    /**Check if a is present a DevelopmentCard with the same Colour and the same Level
     * @param toFind an attribute DevelopmentCard: it's not a real DevelopmentCard instantiated by Game but a requirement for using a Leader ability (check DevelopmentCard documentation)
     * @return true if is present, false if not
     */
    public boolean isPresent(DevelopmentCard toFind){
        for(Stack<DevelopmentCard> slot : slots){
            for(DevelopmentCard card : slot)
                if(card.getColour() == toFind.getColour() && card.getLevel() == toFind.getLevel())
                    return true;
        }
        return false;
    }

    /**Returns the DevelopmentCard on the top in a Slot
     * @param slot the number of Slot
     * @return a DevelopmentCard, null if is not present
     */
    public DevelopmentCard getCard(int slot){
        try {
            DevelopmentCard getCard = slots.get(slot).pop();
            slots.get(slot).push(getCard);
            return getCard;
        }catch(EmptyStackException e){
            return null;
        }
    }

    /**Checks the level rule that can't be added a level i+1 DevelopmentCard over a non-level i DevelopmentCard in a Slot, or if Slot is empty, DevelopmentCard must be level 1
     * @param slot the number of Slot where must be added the DevelopmentCard
     * @param newCard the DevelopmentCard
     * @return true if @newCard respect the rule, false if not
     */
    private boolean checkCardLevelIntegrity(int slot, DevelopmentCard newCard){
        DevelopmentCard oldCard = getCard(slot);
        if(oldCard == null)
            return newCard.getLevel() == 1;
        return oldCard.getLevel() == newCard.getLevel() - 1;
    }

    /**Adds a DevelopmentCard to a slot
     * @param slot the number if Slot
     * @param card the DevelopmentCard to add
     * @throws IndexOutOfBoundsException if slot number is not between 0 and 2 (this never happen)
     * @throws FullCardSlotException if the Slot contains all 3 levels DevelopmentCards and can't contain more DevelopmentCards
     * @throws NonCorrectLevelCardException if the DevelopmentCard doesn't respect the level rules
     */
    public void addCard (int slot, DevelopmentCard card) throws IndexOutOfBoundsException,
                                                                FullCardSlotException,
                                                                NonCorrectLevelCardException {
        if(this.slots.get(slot).isEmpty() && card.getLevel()==1){}
        else {
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

    /**Convert CardSlots in a LightCardSlots instance that could be send to a Client
     * @return a new LightCardSlots instance with the same parameters as the CardSlots instance
     */
    public LightCardSlots convert(){
        LightCardSlots converted = new LightCardSlots();
        ArrayList<LightDevelopmentCard> slots = new ArrayList<>();
        DevelopmentCard card;
        for(int i=0; i<3; i++){
                card = getCard(i);
                if(card!=null)
                    slots.add(card.convert());
                else
                    slots.add(new LightDevelopmentCard());
        }
        converted.setCards(slots);
        return converted;
    }

    /**Flag if game is over
     * @return true if Player has purchased 7 DevelopmentCards, false if not
     */
    public boolean isOver(){
        if(cardCount == 7)
            return true;
        return false;
    }


}


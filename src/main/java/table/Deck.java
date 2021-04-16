package table;

import cards.DevelopmentCard;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    /**
     * The Deck is a class that represents one of the little stacks of cards forming the Development Board
     */
    private ArrayList<DevelopmentCard> cards; //4 cards

    /**
     * Default constructor
     * @param cards A list with the cards in the stack
     */

    public Deck(ArrayList<DevelopmentCard> cards) {
        this.cards = cards;
    }

    /**
     * Method that randomize the card's order in this deck
     */

    public void shuffleDeck(){
        Collections.shuffle(cards);
    }

    /**
     * Method that gives to the caller the first card in this deck, removing that from the stack
     * @return the card on top
     */

    public DevelopmentCard popCard(){
        DevelopmentCard tmpCard;
        int lastIndex = cards.size()-1;
        if(lastIndex<0)
            return null;
        tmpCard = cards.get(lastIndex);
        cards.remove(lastIndex);
        return tmpCard;
    }
}

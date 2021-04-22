package table;

import cards.DevelopmentCard;
import colour.Colour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class Deck {
    /**
     * The Deck is a class that represents one of the little stacks of cards forming the Development Board
     */
    private ArrayList<DevelopmentCard> cards; //4 cards
    private Colour colourDeck;
    private int levelDeck;

    /**
     * Default constructor
     * @param cards A list with the cards in the stack
     */

    public Deck(ArrayList<DevelopmentCard> cards){
        this.cards = cards;
        levelDeck = this.cards.get(0).getLevel();
        colourDeck = this.cards.get(0).getColour();
        cards.forEach((card) ->{
            if(card.getLevel() != levelDeck || card.getColour() != colourDeck)
                throw new InputMismatchException();
        });
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

    public Colour getColourDeck() {
        return colourDeck;
    }

    public int getLevelDeck() {
        return levelDeck;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}

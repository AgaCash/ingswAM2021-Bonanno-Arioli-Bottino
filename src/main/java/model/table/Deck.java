package model.table;

import exceptions.EmptyDeckException;
import model.cards.DevelopmentCard;
import model.colour.Colour;
import model.resources.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class Deck {
    /**
     * The Deck is a class that represents one of the little stacks of cards forming the Development Board
     */
    private ArrayList<DevelopmentCard> cards; //4 model.cards
    private Colour colourDeck;
    private int levelDeck;

    /**
     * Default constructor
     * @param cards A list with the model.cards in the stack
     */

    public Deck(ArrayList<DevelopmentCard> cards){
        this.cards = cards;
        levelDeck = this.cards.get(0).getLevel();
        colourDeck = this.cards.get(0).getColour();
        cards.forEach((card) ->{
            if(card.getLevel() != levelDeck || card.getColour() != colourDeck)
                throw new InputMismatchException();
        });
        shuffleDeck();
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

    public ArrayList<Resource> getCost() throws EmptyDeckException {
        try {
            return (ArrayList<Resource>) cards.get(cards.size() - 1).getCost().clone();
        }catch(IndexOutOfBoundsException e){
            throw new EmptyDeckException("deck is empty!");
        }
    }

    /**Pop the Card in the top of Deck (and removes it)
     * @return a DevelopmentCard instance
     * @throws EmptyDeckException if Deck is empty
     */
    public DevelopmentCard popCard() throws EmptyDeckException {
        DevelopmentCard tmpCard;
        int lastIndex = cards.size()-1;
        if(lastIndex<0)
            throw new EmptyDeckException("Deck is empty!");
        tmpCard = cards.get(lastIndex);
        cards.remove(lastIndex);
        return tmpCard;
    }

    /**Returns the Deck's colour
     * @return a Colour instance
     */
    public Colour getColourDeck() {
        return colourDeck;
    }

    /**Returns the Deck's level
     * @return a Colour instance
     */
    public int getLevelDeck() {
        return levelDeck;
    }

    /**Returns a boolean flag about Deck
     * @return true if Deck is empty, false if not
     */
    public boolean isEmpty(){
        return cards.isEmpty();
    }

    /**Returns the card on the top of Deck without removing it
     * @return
     */
    //only 4 tests
    public DevelopmentCard getCard(){
        if(isEmpty())
            return null;
        return cards.get(cards.size()-1);
    }
}

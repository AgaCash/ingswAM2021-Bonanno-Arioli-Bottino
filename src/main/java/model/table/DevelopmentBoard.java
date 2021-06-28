package model.table;


import clientModel.cards.LightDevelopmentCard;
import clientModel.table.LightDevelopmentBoard;
import exceptions.EmptyDeckException;
import model.cards.DevelopmentCard;
import model.colour.Colour;
import utilities.JsonParser;

import java.util.ArrayList;
import java.util.InputMismatchException;

public final class DevelopmentBoard {
    private final ArrayList<Deck> decks = new ArrayList<>();  //12 decks


    public DevelopmentBoard(){
        initializeBoard();
    }

    /**
     * Method that load all the development model.cards from a configuration Json file
     */

    private void initializeBoard(){
        ArrayList<DevelopmentCard> tmpCards =
                new JsonParser("developmentCards.json").getDevelopmentCards();
        ArrayList<DevelopmentCard> tmpDeck = new ArrayList<>();
        int i = 1;
        for (DevelopmentCard card:tmpCards) {
            tmpDeck.add(card);
            if(i == 4){
                decks.add(new Deck((ArrayList<DevelopmentCard>) tmpDeck.clone()));
                tmpDeck.clear();
                i = 0;
            }
            i++;
        }
    }

    /**
     * Method getter that returns the reference to selected deck by position
     * @param deckNumber index of the deck (0-11)
     * @return the reference of the selected deck
     */
    public Deck getDeck(int deckNumber){
        if(deckNumber<0 || deckNumber >decks.size()-1)
            throw new InputMismatchException();
        return decks.get(deckNumber);
    }

    /**
     * Method getter that returns the reference to selected deck by model.colour
     * @param colour model.colour of the deck
     * @return  the reference of the selected deck
     */
    public Deck getDeck(Colour colour) throws EmptyDeckException {
        for (Deck d:decks)
            if(d.getColourDeck()==colour && !d.isEmpty())
                return  d;
        throw new EmptyDeckException("Deck is empty!");
    }

    /**
     * Method that pop the first card of the selected deck (by model.colour)
     * @param colour model.colour of the deck
     * @return the first card in the deck
     */
    public DevelopmentCard popCardFromDeckColour(Colour colour) throws EmptyDeckException {
        return this.getDeck(colour).popCard();
    }

    /**Converts the current DevelopmentBoard state in a LightDevelopmentBoard instance for LightModel
     * @return a LightDevelopmentBoard instance
     */
    public LightDevelopmentBoard convert(){
        LightDevelopmentBoard board = new LightDevelopmentBoard();
        ArrayList<LightDevelopmentCard> cards = new ArrayList<>();
        for(Deck deck : decks)
            try {
                cards.add(deck.getCard().convert());
            }catch(NullPointerException e){
                cards.add(new LightDevelopmentCard());
            }
        board.setDecks(cards);
        return board;
    }

}


package model.table;


import model.cards.DevelopmentCard;
import model.colour.Colour;
import model.utilities.JsonParser;
import java.util.ArrayList;

public final class DevelopmentBoard {
    private static DevelopmentBoard instance = null;
    private final ArrayList<Deck> decks = new ArrayList<>();  //12 decks

    private DevelopmentBoard(){
        initializeBoard();
    }

    public static DevelopmentBoard getDevBoardInstance(){
        if (instance == null)
            //synchronized(DevelopmentBoard.class){
            //    if(instance == null)
            //        instance = new DevelopmentBoard();
            //    }   double-checked locking
            instance = new DevelopmentBoard();
        return instance;
    }

    public void deleteInstance(){
        instance = null;
    }

    /**
     * Method that load all the development model.cards from a configuration Json file
     */

    private void initializeBoard(){
        ArrayList<DevelopmentCard> tmpCards =
                new JsonParser("src/main/resources/developmentCards.json").getDevelopmentCards();
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
            return null;
        else
            return decks.get(deckNumber);
    }

    /**
     * Method getter that returns the reference to selected deck by model.colour
     * @param colour model.colour of the deck
     * @return  the reference of the selected deck
     */
    public Deck getDeck(Colour colour){
        Deck tmpDeck = null;
        for (Deck d:decks) {
            if(d.getColourDeck()==colour && !d.isEmpty())
                tmpDeck = d;
        }
        return tmpDeck;
    }

    /**
     * Method that pop the first card of the selected deck (by index)
     * @param deckNumber index of the deck (0-11)
     * @return the card on the top of the selected deck
     */

    public DevelopmentCard popCardFromDeck(int deckNumber){
        Deck d = this.getDeck(deckNumber);
        if(d == null)
            return null;
        else
            return d.popCard();
    }

    /**
     * Method that pop the first card of the selected deck (by model.colour)
     * @param colour model.colour of the deck
     * @return the first card in the deck
     */
    public DevelopmentCard popCardFromDeckColour(Colour colour){
        Deck d = this.getDeck(colour);
        if(d == null)
            return null;
        else
            return d.popCard();
    }

}
